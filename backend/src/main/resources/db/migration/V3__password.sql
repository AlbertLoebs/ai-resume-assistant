-- V3__password.sql
-- Adds password_hash column to the users table.
--
-- Notes:
-- * NOT NULL: every user must have a hash; plain-text passwords never touch the DB.
-- * DEFAULT '' is a migration-safe placeholder so Flyway can apply this to a table
--   that already has rows (dev databases). Drop the default after backfilling real
--   hashes, or TRUNCATE users first in dev before re-seeding.

ALTER TABLE users ADD COLUMN password_hash TEXT NOT NULL DEFAULT '';
