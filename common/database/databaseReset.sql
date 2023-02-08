SELECT pg_terminate_backend(pg_stat_activity.pid)
 FROM pg_stat_activity
WHERE pg_stat_activity.datname = 'telemetry_db'
  AND pid <> pg_backend_pid();

drop database "telemetry_db";
create database "telemetry_db";

SELECT pg_terminate_backend(pg_stat_activity.pid)
FROM pg_stat_activity
WHERE pg_stat_activity.datname = 'telecommand_db'
  AND pid <> pg_backend_pid();

drop database "telecommand_db";
create database "telecommand_db";


SELECT pg_terminate_backend(pg_stat_activity.pid)
FROM pg_stat_activity
WHERE pg_stat_activity.datname = 'satellite_io_db'
  AND pid <> pg_backend_pid();

drop database "satellite_io_db";
create database "satellite_io_db";