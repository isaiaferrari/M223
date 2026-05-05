DELETE FROM Blog;

INSERT INTO Blog (id, title, published_date, category, author, likes, content)
VALUES (NEXT VALUE FOR blog_seq,'Testare le Applicazioni con JUnit 5','2024-05-10','Testing','luca',25,
        'JUnit 5 introduce numerosi miglioramenti rispetto alle versioni precedenti. Questo articolo illustra le annotazioni più importanti e le buone pratiche per scrivere test puliti ed efficaci nelle applicazioni Java.'),
       (NEXT VALUE FOR blog_seq,'Buone Pratiche per le REST API','2024-06-01','Architettura','sara',18,
        'Progettare una REST API ben strutturata richiede attenzione alla scelta dei nomi, ai codici di stato HTTP, al versionamento e alla gestione degli errori. Scopriamo insieme i principi fondamentali da seguire.');