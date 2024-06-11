# ClinicSystem

Este é um sistema de gerenciamento de clínicas desenvolvido em Java. O sistema permite o gerenciamento de pacientes, psicólogos e consultas.

## Funcionalidades

- Cadastro de pacientes
- Cadastro de psicólogos
- Agendamento de consultas
- Edição e exclusão de consultas

## Requisitos

- Java Development Kit (JDK) 8 ou superior
- MySQL 5.7 ou superior
- Biblioteca JDBC do MySQL

## Configuração do Ambiente

### 1. Instalar o JDK

Certifique-se de que o JDK está instalado em sua máquina. Você pode fazer o download [aqui](https://www.oracle.com/java/technologies/javase-downloads.html).

### 2. Instalar o MySQL

Faça o download e instale o MySQL Community Server a partir do [site oficial](https://dev.mysql.com/downloads/mysql/).

### 3. Importando e Executando o Projeto no Eclipse
Clone o repositório: git clone https://github.com/Dih-M/ClinicSystem.git

Importe o projeto no Eclipse:

Abra o Eclipse. Vá para File > Import. Selecione Existing Maven Projects e clique em Next. Navegue até o diretório onde o projeto foi clonado e clique em Finish.

Execute a aplicação: No Package Explorer, navegue até a classe principal do projeto "WelcomeWindow.java"

### 4. Configurar o Banco de Dados

1. Inicie o servidor MySQL. 

2. Crie um banco de dados chamado `clinic`.
CREATE DATABASE clinic;

3. Crie as tabelas necessárias. As tabelas serão criadas automaticamente quando o sistema for executado pela primeira vez.

4. Configurar a Conexão com o Banco de Dados
Atualize o arquivo DatabaseManager.java com suas credenciais de banco de dados MySQL:

private static final String URL = "jdbc:mysql://localhost:3306/clinic";
private static final String USER = "seu_usuario";
private static final String PASSWORD = "sua_senha";

5. Baixar a Biblioteca JDBC do MySQL:
Faça o download da biblioteca JDBC do MySQL a partir do site oficial. Adicione o arquivo .jar ao classpath do seu projeto.
