## Что поднимается

Команда `docker compose up` запускает 3 сервиса:

- `vault` на `http://localhost:8200`
- `keycloak` на `http://localhost:8081`
- `backend` на `http://localhost:8080`

Все сервисы запускаются из готовых образов:

- `mabdqs/hashicorp-sso-demo:latest`
- `mabdqs/hashicorp-sso-keycloak:latest`
- `mabdqs/hashicorp-sso-vault:latest`

## Демо-данные

### Keycloak

- realm: `demo`
- client: `backend-app`
- client secret: `backend-secret`
- user: `demo-user`
- password: `demo-password`

### Vault

- root token: `root-token`
- secret path: `secret/hashicorp-sso-demo`
- key: `app.example-secret`
- value: `demo-value`

## Запуск проекта

### 1. Поднять все сервисы

```powershell
docker compose up -d
```

### 2. Проверить, что контейнеры запущены

```powershell
docker compose ps
```

Ожидаемо должны быть запущены:

- `vault`
- `keycloak`
- `backend`

### 3. Остановить проект

```powershell
docker compose down
```

## Сценарий демонстрации

### Шаг 1. Проверить, что backend доступен

Откройте:

`http://localhost:8080/api/public`

Ожидаемый результат:

- backend отвечает JSON
- в ответе есть:
  - имя сервиса
  - login endpoint
  - список защищённых endpoint’ов

Пример:

```json
{
  "service": "hashicorp-sso-demo",
  "login": "/oauth2/authorization/keycloak",
  "securedEndpoints": ["/api/me", "/api/secret"]
}
```

### Шаг 2. Запустить SSO-авторизацию через Keycloak

Откройте в браузере:

`http://localhost:8080/oauth2/authorization/keycloak`

После этого backend перенаправит вас в Keycloak на страницу логина.

### Шаг 3. Войти под тестовым пользователем

Используйте:

- username: `demo-user`
- password: `demo-password`

После успешного входа произойдет редирект обратно в backend.

### Шаг 4. Проверить данные текущего пользователя

Откройте:

`http://localhost:8080/api/me`

Что демонстрирует этот шаг:

- пользователь аутентифицирован через Keycloak
- backend принимает и валидирует токен
- backend видит данные текущего пользователя

Ожидаемый результат:

- JSON с идентификатором пользователя
- список authority/scopes

### Шаг 5. Проверить получение секрета из Vault

Откройте:

`http://localhost:8080/api/secret`

Что демонстрирует этот шаг:

- backend читает секрет из Vault
- значение не хранится в коде или локальном конфиге приложения

Ожидаемый результат:

```json
{
  "app.example-secret": "demo-value"
}
```