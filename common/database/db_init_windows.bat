set PGPASSWORD=123456

cd C:\"Program Files"\PostgreSQL\14\bin\
psql -U postgres< C:\Users\volkan.ulutas\Desktop\databaseReset.sql


(
echo TRUNCATE fileoutput CASCADE;
) | C:\"Program Files"\PostgreSQL\14\bin\psql -h localhost -p 5432 -U postgres -d Oculus-DB