CREATE TABLE card_summary (
  id              VARCHAR(255) NOT NULL,
  initial_value   INTEGER,
  issued_at       DATETIME(6),
  remaining_value INTEGER,
  PRIMARY KEY (id)
) ENGINE = InnoDB;

CREATE TABLE token_entry (
  processor_name VARCHAR(255) NOT NULL,
  segment        INTEGER      NOT NULL,
  owner          VARCHAR(255),
  timestamp      VARCHAR(255) NOT NULL,
  token          LONGBLOB,
  token_type     VARCHAR(255),
  PRIMARY KEY (processor_name, segment)
) ENGINE = InnoDB;
