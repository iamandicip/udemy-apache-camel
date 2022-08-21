# Apache Camel course on Udemy

## Apache Camel

- Open source Enterprise integration framework
  - easily integrate systems consuming or producing data
- Inspired by [Enterprise Integration Patterns](https://www.enterpriseintegrationpatterns.com/) book by Gregor Hohpe and Bobby Woolf
  - evolved to Microservice Architectures and Cloud
- Lean - lightweight and extensible
  - component architecture
  - supports many protocols, transports and data formats
  - supports many databases, message queues, APIs, cloud integrations, etc
  - provides Domain Specific Language (DSL) for application integration

## Camel Terminology

### Camel context - (0..n) Routes + Components +  ..., etc

- **Endpoint** - reference to a queue, database or a file
- **Route** - Endpoint + Processor(s) + Transformer(s)
- **Components** - Extensions (Kafka, JSON, JMS, etc)
- **Tranformation**:
  - data format transformation - XML to JSON
  - data type transformation - String to CurrencyConversionBean
- **Message** - Body + Headers + Attachments
- **Exchange** - Request + Response
  - Exchange id
  - Message Exchange Pattern (MEP) - InOnly / InOut
  - Input message and (Optional) Output message
