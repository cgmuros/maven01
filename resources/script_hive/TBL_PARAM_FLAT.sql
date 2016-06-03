use cdeexp;
CREATE EXTERNAL TABLE IF NOT EXISTS TBL_PARAM_FLAT(
	nombre_tabla string,
	nombre_campo string,
	largo_caracteres string
)
ROW FORMAT DELIMITED FIELDS TERMINATED BY '|'
LOCATION '/chile/desarrollo/002_isban/data/des/staging/cde/explotacion/TBL_PARAM_FLAT'
