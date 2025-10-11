-- Automation: Trigger to prevent double booking at same time for same patient

CREATE OR REPLACE FUNCTION prevent_double_booking()
RETURNS TRIGGER AS $$
BEGIN
  IF EXISTS (
    SELECT 1 FROM consulta c
    WHERE c.usuario_id = NEW.usuario_id
      AND c.data_consulta = NEW.data_consulta
      AND c.hora_consulta = NEW.hora_consulta
      AND c.id <> COALESCE(NEW.id, -1)
  ) THEN
    RAISE EXCEPTION 'Paciente ja possui consulta neste horario';
  END IF;
  RETURN NEW;
END;
$$ LANGUAGE plpgsql;

DROP TRIGGER IF EXISTS trg_consulta_no_double_booking ON consulta;
CREATE TRIGGER trg_consulta_no_double_booking
BEFORE INSERT OR UPDATE ON consulta
FOR EACH ROW EXECUTE PROCEDURE prevent_double_booking();
