-- V4__resumes.sql
-- Creates the resumes table.
--
-- Columns:
--   id          UUID, primary key
--   user_id     UUID, NOT NULL, FK → users(id), ON DELETE CASCADE
--               (deleting a user wipes their resumes — no orphan rows)
--   title       text, NOT NULL
--   content     text, NOT NULL (markdown or plain text body)
--   created_at  TIMESTAMPTZ, NOT NULL, default NOW()
--   updated_at  TIMESTAMPTZ, NOT NULL, default NOW()
--
-- Index:
--   resumes_user_id_idx  on (user_id) — speeds up "list my resumes" queries.
--
-- Notes:
--   * No UNIQUE constraint on title — a user can have multiple resumes with the same name.
--   * ON DELETE CASCADE means if a user is deleted, their resumes go too. That's the
--     right behaviour for "I want to delete my account" — orphan resumes are useless.

CREATE TABLE resumes (
    -- TODO: define columns here
    id UUID primary key,
    user_id UUID not null references users(id) ON delete cascade,
    title text not null,
    content text not null,
    created_at TIMESTAMPTZ not null,
    updated_at TIMESTAMPTZ not null
);

-- TODO: CREATE INDEX resumes_user_id_idx ON resumes (user_id);
CREATE INDEX resumes_user_id_idx ON resumes(user_id)