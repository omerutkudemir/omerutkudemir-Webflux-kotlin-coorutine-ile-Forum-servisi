-- entry tablosu
CREATE TABLE IF NOT EXISTS entry (
                                     id BIGSERIAL PRIMARY KEY,
                                     user_id UUID NOT NULL,
                                     subject_header TEXT NOT NULL,
                                     subject_description TEXT NOT NULL,
                                     created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                     like_count BIGINT
);

-- entry_response tablosu
CREATE TABLE IF NOT EXISTS entry_response (
                                              id BIGSERIAL PRIMARY KEY,
                                              user_id UUID NOT NULL,
                                              entry_id BIGINT,  -- entry tablosuna FK
                                              response TEXT NOT NULL,
                                              like_count BIGINT,
                                              created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                              CONSTRAINT fk_entry_response_entry
                                              FOREIGN KEY (entry_id)
    REFERENCES entry(id)
    ON DELETE CASCADE
    );

-- like_entry tablosu
CREATE TABLE IF NOT EXISTS like_entry (
                                          id BIGSERIAL PRIMARY KEY,
                                          user_id UUID NOT NULL,
                                          entry_id BIGINT,
                                          CONSTRAINT fk_like_entry_entry
                                          FOREIGN KEY (entry_id)
    REFERENCES entry(id)
    ON DELETE CASCADE
    );

-- like_entry_response tablosu
CREATE TABLE IF NOT EXISTS like_entry_response (
                                                   id BIGSERIAL PRIMARY KEY,
                                                   user_id UUID NOT NULL,
                                                   entry_response_id BIGINT,
                                                   CONSTRAINT fk_like_entry_response_entry_response
                                                   FOREIGN KEY (entry_response_id)
    REFERENCES entry_response(id)
    ON DELETE CASCADE
    );

-- author_follow tablosu
CREATE TABLE IF NOT EXISTS author_follow (
                                             id BIGSERIAL PRIMARY KEY,
                                             author_id UUID NOT NULL,
                                             reader_id UUID NOT NULL
);
