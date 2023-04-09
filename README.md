# Teste Técnico Back End - Attornatus

## Descrição
API criada com Spring Boot para gerenciamento de pessoas e seus endereços, essa API deve permitir:
- Criar, editar, consultar e listar pessoas.
- Criar endereços, listar e informar qual é o principal de cada individuo.

Foi adicionado como extras.
- Deleção de pessoa.
- Sistema de atualização de endereços para garantir que apenas um seja o principal.
- Edição de endereço.
- Deleção de endereço.

Requisitos:
- Todas as requisições apenas trabalham com JSON.
- Foi utilizado o banco de dados H2.

## Rotas do projeto

### Pessoas
| HTTP METHOD | URL | Descriçao |
| ------ | ------ | ----------- |
|**<font color="blue">GET</font>**|`http://localhost:8080/api/person/v1/{idPessoa}`   | Consultar pessoa |
|**<font color="blue">POST</font>**|`http://localhost:8080/api/person/v1/` | Criar pessoa |
|**<font color="blue">GET</font>**| `http://localhost:8080/api/person/v1`   | Consultar todos endereços da pessoa |
|**<font color="blue">DELETE</font>**| `http://localhost:8080/api/person/v1/{idPessoa}`   | Deletar pessoa |
|**<font color="blue">PUT</font>**|`http://localhost:8080/api/person/v1/` | Editar pessoa |
|**<font color="blue">GET</font>**| `http://localhost:8080/api/person/v1/addresses/{idPessoa}`   | Consultar endereços das pessoas |
|**<font color="blue">GET</font>**| `http://localhost:8080/api/person/v1/mainAdress/{idPessoa}`   | Consultar endereço principal da pessoa  |

### Endereços
| HTTP METHOD | URL | Descriçao |
| ------ | ------ | ----------- |
|**<font color="blue">POST</font>**|`http://localhost:8080/api/address/v1/create/{idPessoa}`   | Adicionar endereço a uma pessoa  |
|**<font color="blue">PUT</font>**|`http://localhost:8080/api/address/v1` | Editar endereço |
|**<font color="blue">DELETE</font>**| `http://localhost:8080/api/address/v1/{idEndereço}`   | Deletar endereço |

## Documentação com Swagger
Para ler a documentação swagger.
1. Execute a aplicação 
2. Acesse localmente: http://localhost:8080/swagger-ui/index.html

## Banco de dados H2
Para acessar o banco de dados:
1. Execute a aplicação.
2. Acesse localmente: http://localhost:8080/h2-console


| Configuração | Valor                   |
| ------------ | -----------------------|
| Driver Class | `org.h2.Driver`        |
| JDBC URL     | `jdbc:h2:mem:testdb`   |
| User Name    | `sa`                    |
| Senha        | (deixe em branco)      |

## Tecnologias
- JDK 17
- Maven
- Spring Boot 
- H2 DATABASE
- Mockito e JUnit 5
- Swagger
