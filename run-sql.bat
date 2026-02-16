cd pulse-check-api
echo "ALTER TABLE monitors ADD COLUMN IF NOT EXISTS alerted BOOLEAN NOT NULL DEFAULT FALSE;" > temp.sql
./mvnw spring-boot:run -Dspring.profiles.active=dev
