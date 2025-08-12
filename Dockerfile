# ======================================================================
# STAGE 1: Build - Usa uma imagem com Maven e JDK para compilar o app
# ======================================================================
FROM maven:3.9.6-eclipse-temurin-17 AS build

# Define o diretório de trabalho dentro do contêiner
WORKDIR /app

# Copia o pom.xml e baixa as dependências primeiro para otimizar o cache
COPY pom.xml .
RUN mvn dependency:go-offline

# Copia o resto do código-fonte do seu projeto
COPY src ./src

# Compila e empacota a aplicação, JÁ PULANDO OS TESTES (-DskipTests)
RUN mvn clean package -DskipTests

# ======================================================================
# STAGE 2: Production - Usa uma imagem leve apenas com o Java (JRE)
# ======================================================================
FROM eclipse-temurin:17-jre-alpine

# Define o diretório de trabalho
WORKDIR /app

# Copia o arquivo .jar gerado no estágio de build para a imagem final
COPY --from=build /app/target/*.jar app.jar

# Expõe a porta em que a aplicação Spring Boot roda
EXPOSE 8080

# Comando final para iniciar a sua aplicação quando o contêiner rodar
ENTRYPOINT ["java", "-jar", "app.jar"]