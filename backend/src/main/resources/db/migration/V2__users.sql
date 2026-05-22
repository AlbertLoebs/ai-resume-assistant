-- V2__users.sql
-- Creates the users table.
--
-- Columns to define:
--   id            UUID, primary key
--   email         text, NOT NULL, UNIQUE
--   display_name  text, nullable
--   created_at    TIMESTAMPTZ, NOT NULL, default NOW()
--   updated_at    TIMESTAMPTZ, NOT NULL, default NOW()
--
-- Notes:
-- * Postgres has a native UUID type — no extension needed.
-- * Use TIMESTAMPTZ (timestamp WITH time zone) for created_at / updated_at —
--   it's the only sane choice for any column that's ever queried across regions.
-- * The UNIQUE constraint on email gives you a free index for lookups.
--
-- Once the table exists, run the app. Flyway should pick this up as V2
-- and report "Migrating schema 'public' to version 2 - users".

CREATE TABLE users (
    -- TODO: define columns here
    id UUID primary key,
    email text not null unique,
    display_name text,
    created_at TIMESTAMPTZ not null default now(),
    updated_at TIMESTAMPTZ not null default now()
);

-- TODO (optional): explicit index on email for faster case-insensitive lookups
-- e.g. CREATE INDEX users_email_lower_idx ON users (LOWER(email));
CREATE INDEX users_email_lower_idx ON users (LOWER(email));
