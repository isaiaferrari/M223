CREATE SEQUENCE IF NOT EXISTS blog_seq START WITH 1 INCREMENT BY 1;

CREATE TABLE IF NOT EXISTS Blog (
                                    id             BIGINT        PRIMARY KEY,
                                    title          VARCHAR(100)  NOT NULL,
                                    published_date DATE          NOT NULL,
                                    category       VARCHAR(50)   NOT NULL,
                                    author         VARCHAR(50)   NOT NULL,
                                    likes          INT           NOT NULL DEFAULT 0,
                                    content        CLOB          NOT NULL
);