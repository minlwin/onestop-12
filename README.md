# onestop-12
FInal Project

## Create React Project

Create React Project with vite

```
npm create vite@latest

> npx
> "create-vite"

│
◇  Project name:
│  balance-web
│
◇  Select a framework:
│  React
│
◇  Select a variant:
│  TypeScript
│
◇  Scaffolding project in /Users/minlwin/git/onestop-12/balance-web...
│
└  Done. Now run:

  cd balance-web
  npm install
  npm run dev
```

Adding Dependencies

```
cd balance-web

# Dependency for Form Processing
npm i react-hook-form

# Dependency for Navigation
npm i react-router

# Dependency for REST Client
npm i axios
```

## Prepare Database

Pulling PostgreSQL Docker Image
```
docker pull postgres
```

Docker Compose file for PostgreSQL
```
networks:
  balance-net:

volumes:
  balance-volume:

services:
  db:
    image: postgres
    container_name: balance-db
    environment:
      - TZ=Asia/Yangon
      - POSTGRES_USER=balance
      - POSTGRES_PASSWORD=balance
      - POSTGRES_DB=balance
    networks:
      - balance-net
    ports:
      - 5432:5432
    volumes:
      - balance-volume:/var/lib/postgresql/data
```

Running Docker Compose File
```
docker compose up -d
```