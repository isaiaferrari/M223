DELETE FROM Blog;

INSERT INTO Blog (id, title, published_date, category, author, likes, content)
VALUES (NEXT VALUE FOR blog_seq,'Introduzione a Spring Boot','2024-01-15','Tecnologia','mario',12,
        'Spring Boot semplifica la creazione di applicazioni Java stand-alone pronte per la produzione. In questo articolo esploriamo i consigli più utili per chi inizia ad avvicinarsi a questo potente framework.'),
       (NEXT VALUE FOR blog_seq,'Come Imparare Java Velocemente','2024-03-22','Formazione','giulia',7,
        'Java è uno dei linguaggi di programmazione più diffusi al mondo. In questo post condividiamo le strategie più efficaci per accelerare il percorso di apprendimento e padroneggiare il linguaggio in poco tempo.' );