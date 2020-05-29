Task: create a command-line chat client, that will be listenoing for messages with user and content, and will be displaying them.
Also, it should be able to send a chat message, that others can read it.
JSON format of message: `{"user":"Arek","message":"Hello World!"}`

Server is Rabbit MQ, free tier from www.cloudamqp.com.
* HOST: `bear.rmq.cloudamqp.com`
* USER: `fwknozud`
* VIRTUAL HOST: `fwknozud`
* QUEUE: `chat`
* PASSWORD: `omy9Kbzj6Q56GOqb27_iP8MXAK1CVDoL`
* EXCHANGE NAME: `chat`
* EXCHANGE TYPE: `fanout`

