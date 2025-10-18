## Coletor de Dados Oberon (Java)

O projeto **Oberon - Coleta Java** é o componente de serviço que reside nas máquinas dos clientes  e é responsável pela **coleta contínua e em tempo real** de métricas vitais de hardware e rede. Seu principal objetivo é alimentar o sistema de monitoramento central (a Aplicação Web Oberon) com dados precisos, permitindo a detecção de anomalias e a emissão de alertas.

-----

## Destaques Funcionais

Este coletor de dados foi projetado para operar de forma robusta e discreta em segundo plano, focando nas seguintes tarefas:

  * **Coleta de Dados de Rede:** Monitora ativamente o tráfego de rede (bytes enviados/recebidos) por meio da API Looca.
  * **Registro de Alertas:** Envia as métricas coletadas para o banco de dados central (via JDBC) para persistência e análise posterior pela Aplicação Web.
  * **Identificação do Ativo:** Realiza o login na Aplicação Web para obter o ID da máquina e da empresa, garantindo que os dados sejam associados ao ativo correto.
  * **Gerenciamento de Logs:** Utiliza um sistema de logger interno (`LoggerOberon`) para registrar eventos importantes, erros e a atividade de coleta em um arquivo local (`log_oberon.txt`).
  * **Conexão Dinâmica ao Banco de Dados:** Permite a conexão com diferentes provedores de banco de dados (configurado para MySQL e SQL Server), aumentando a flexibilidade da infraestrutura.

-----

##  Tecnologias e Dependências

O projeto é desenvolvido em Java e utiliza a API Looca para acesso simplificado às informações do sistema operacional e hardware.

| Tecnologia | Propósito |
| :--- | :--- |
| **Java (JDK 8+)** | Linguagem de desenvolvimento principal. |
| **Looca API** | Biblioteca de código aberto da sptech para coleta de dados de sistema (CPU, RAM, Disco, Rede, etc.). |
| **JDBC** | API Java para conexão e execução de consultas ao banco de dados (MySQL e SQL Server). |
| **Log4j** | (Implícito/Recomendado ou Customizado) para gerenciamento robusto de logs. |
| **Maven/Gradle** | (Implícito pelo `pom.xml`) Gerenciador de dependências e construção do projeto. |

-----

##  Arquitetura do Componente

A arquitetura segue um padrão de serviço em *polling* (sondagem), onde a coleta de dados e a inserção no banco de dados ocorrem em intervalos regulares definidos.

### strutura de Pacotes

```
oberon-coleta-java/
└── looca-api/
    ├── src/
    │   └── main/
    │       └── java/
    │           ├── com.github.britooo.looca.api.core/  # Core da API Looca (importado)
    │           ├── com.github.britooo.looca.api.group.rede/ # Módulos de Rede da Looca
    │           └── com.oberon.monitoramento/           # Lógica de negócio do coletor
    │               ├── Main.java                       # Ponto de entrada (Login e Inicialização)
    │               ├── Conexao.java                    # Gerenciamento de conexões JDBC
    │               ├── MonitorRedeService.java         # Serviço de coleta de métricas de Rede
    │               ├── RegistroRede.java               # Modelo de dados para registros de Rede
    │               ├── RegistroRedeDAO.java            # Camada de acesso a dados (CRUD)
    │               └── LoggerOberon.java               # Utilitário para gravação de logs
    ├── log_oberon.txt                  # Arquivo de log gerado pelo LoggerOberon
    └── pom.xml                         # Arquivo de configuração Maven (dependências)
```

### Fluxo de Execução Principal (`Main.java`)

1.  **Inicialização:** O método `main` é executado.
2.  **Login:** Tenta autenticar o cliente na Aplicação Web para obter o `idEmpresa` e o `idMaquina`.
3.  **Coleta e Inserção:** Após o login bem-sucedido, entra em um *loop* contínuo com um intervalo de tempo (`Thread.sleep`).
4.  **Serviço de Coleta:** Chama `MonitorRedeService.iniciarMonitoramento()`.
5.  **Gravação de Dados:** O serviço coleta dados usando a API Looca e, em seguida, utiliza `RegistroRedeDAO.inserirDadosRede()` para persistir as informações no banco de dados.

-----

##  Configuração e Execução

### pré-requisitos

1.  **Java Development Kit (JDK):**  Amazon Carreto 21.0.8
2.  **Maven/Gradle:** Para resolver dependências e compilar o projeto.
3.  **Banco de Dados:** Acesso a uma instância do MySQL ou SQL Server (as credenciais e configurações de conexão devem ser ajustadas na classe `Conexao.java`).

### \. Clonar e Instalar Dependências

Utilize o gerenciador de pacotes (ex: Maven, com base no `pom.xml`) para baixar as dependências, principalmente a API Looca.

```bash
# Navegue até o diretório do projeto (looca-api)
cd oberon-coleta-java/looca-api
# Comando Maven para instalar dependências
mvn clean install
```

### \. Configuração do Banco de Dados

Revise e ajuste as credenciais de conexão na classe `com.oberon.monitoramento.Conexao.java`. O projeto precisa ser configurado para o tipo de banco de dados (MySQL ou SQL Server) que está sendo usado pela Aplicação Web.

### \. Executar o Coletor

O projeto pode ser executado como um JAR *with dependencies* (como indicado pelo `target/looca-api-1.0.0-jar-with-dependencies.jar`):

```bash
# Execução a partir do JAR
java -jar looca-api-1.0.0-jar-with-dependencies.jar
```

Alternativamente, durante o desenvolvimento, pode ser executado via IDE ou pelo comando `mvn exec:java`.

-----

##  Licença

Este projeto está sob a Licença pública, conforme definido no repositório original.

> Copyright (c) 2022 BandTec Digital School - agora é São Paulo Tech School