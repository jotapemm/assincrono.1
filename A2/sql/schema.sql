-- Database schema for consultasdb
-- Tables: endereco, usuario, consulta

CREATE TABLE IF NOT EXISTS endereco (
    id SERIAL PRIMARY KEY,
    estado VARCHAR(2) NOT NULL,
    cidade VARCHAR(120) NOT NULL,
    rua VARCHAR(255) NOT NULL,
    numero VARCHAR(20) NOT NULL,
    cep VARCHAR(20) NOT NULL
);

CREATE TABLE IF NOT EXISTS usuario (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    cpf VARCHAR(11) NOT NULL UNIQUE,
    email VARCHAR(255) NOT NULL UNIQUE,
    idade INTEGER NOT NULL,
    telefone VARCHAR(20) NOT NULL,
    endereco_id INTEGER REFERENCES endereco(id) ON DELETE SET NULL,
    created_at TIMESTAMP NOT NULL DEFAULT now(),
    updated_at TIMESTAMP NOT NULL DEFAULT now()
);

DO $$ BEGIN
    IF NOT EXISTS (SELECT 1 FROM pg_type WHERE typname = 'consulta_status') THEN
        CREATE TYPE consulta_status AS ENUM ('AGENDADA','CONFIRMADA','CANCELADA','REAGENDADA');
    END IF;
END $$;

CREATE TABLE IF NOT EXISTS consulta (
    id SERIAL PRIMARY KEY,
    usuario_id INTEGER NOT NULL REFERENCES usuario(id) ON DELETE CASCADE,
    data_consulta DATE NOT NULL,
    hora_consulta TIME NOT NULL,
    status consulta_status NOT NULL DEFAULT 'AGENDADA',
    observacoes TEXT,
    created_at TIMESTAMP NOT NULL DEFAULT now(),
    updated_at TIMESTAMP NOT NULL DEFAULT now()
);

-- Indexes
CREATE INDEX IF NOT EXISTS idx_usuario_endereco ON usuario(endereco_id);
CREATE INDEX IF NOT EXISTS idx_usuario_cpf ON usuario(cpf);
CREATE INDEX IF NOT EXISTS idx_consulta_usuario ON consulta(usuario_id);
CREATE INDEX IF NOT EXISTS idx_consulta_data ON consulta(data_consulta);

-- Trigger to auto-update updated_at columns
CREATE OR REPLACE FUNCTION set_updated_at()
RETURNS TRIGGER AS $$
BEGIN
  NEW.updated_at = now();
  RETURN NEW;
END;
$$ LANGUAGE plpgsql;

DROP TRIGGER IF EXISTS trg_usuario_updated_at ON usuario;
CREATE TRIGGER trg_usuario_updated_at BEFORE UPDATE ON usuario
FOR EACH ROW EXECUTE PROCEDURE set_updated_at();

DROP TRIGGER IF EXISTS trg_consulta_updated_at ON consulta;
CREATE TRIGGER trg_consulta_updated_at BEFORE UPDATE ON consulta
FOR EACH ROW EXECUTE PROCEDURE set_updated_at();

-- Some parameterized example queries (as comments) avoiding SELECT *
-- SELECT id, nome, cpf, email FROM usuario WHERE cpf = $1;
-- SELECT id, estado, cidade, rua, numero, cep FROM endereco WHERE LOWER(cidade) LIKE LOWER('%'||$1||'%');
-- SELECT id, usuario_id, data_consulta, hora_consulta, status FROM consulta WHERE data_consulta = $1;

-- =============================
-- Auditoria de operações (ATIVIDADE 04)
-- Tabela de auditoria com os campos: ID, Operação, Data
-- =============================

CREATE TABLE IF NOT EXISTS auditoria (
    id SERIAL PRIMARY KEY,
    operacao TEXT NOT NULL,
    data TIMESTAMP NOT NULL DEFAULT now()
);

-- Função genérica para registrar operações
CREATE OR REPLACE FUNCTION audit_changes()
RETURNS TRIGGER AS $$
DECLARE
  op TEXT := TG_OP;
  tbl TEXT := TG_TABLE_NAME;
  rec_id BIGINT;
BEGIN
  IF (TG_OP = 'INSERT') THEN
    rec_id := NEW.id;
  ELSIF (TG_OP = 'UPDATE') THEN
    rec_id := NEW.id;
  ELSE
    rec_id := OLD.id;
  END IF;
  INSERT INTO auditoria (operacao, data)
  VALUES (tbl || ':' || op || ' id=' || COALESCE(rec_id, -1), now());
  RETURN COALESCE(NEW, OLD);
END;
$$ LANGUAGE plpgsql;

-- Triggers de auditoria para as tabelas principais
DROP TRIGGER IF EXISTS trg_usuario_auditoria ON usuario;
CREATE TRIGGER trg_usuario_auditoria
AFTER INSERT OR UPDATE OR DELETE ON usuario
FOR EACH ROW EXECUTE PROCEDURE audit_changes();

DROP TRIGGER IF EXISTS trg_endereco_auditoria ON endereco;
CREATE TRIGGER trg_endereco_auditoria
AFTER INSERT OR UPDATE OR DELETE ON endereco
FOR EACH ROW EXECUTE PROCEDURE audit_changes();

DROP TRIGGER IF EXISTS trg_consulta_auditoria ON consulta;
CREATE TRIGGER trg_consulta_auditoria
AFTER INSERT OR UPDATE OR DELETE ON consulta
FOR EACH ROW EXECUTE PROCEDURE audit_changes();

-- =============================
-- View da agenda dos pacientes (ATIVIDADE 04)
-- =============================

CREATE OR REPLACE VIEW vw_agenda_consultas AS
SELECT 
    c.id,
    c.usuario_id,
    c.data_consulta,
    c.hora_consulta,
    c.status,
    c.observacoes,
    u.nome AS u_nome,
    u.cpf AS u_cpf
FROM consulta c
JOIN usuario u ON u.id = c.usuario_id;
