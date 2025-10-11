-- Example SELECTs following best practices

-- Buscar um paciente por CPF (apenas campos necessários)
SELECT u.id, u.nome, u.cpf, u.email, u.telefone
FROM usuario u
WHERE u.cpf = $1;

-- Listar endereços por cidade
SELECT e.id, e.estado, e.cidade, e.rua, e.numero, e.cep
FROM endereco e
WHERE LOWER(e.cidade) LIKE LOWER('%' || $1 || '%')
ORDER BY e.cidade;

-- Listar consultas de um paciente em uma data
SELECT c.id, c.data_consulta, c.hora_consulta, c.status
FROM consulta c
WHERE c.usuario_id = $1 AND c.data_consulta = $2
ORDER BY c.hora_consulta;
