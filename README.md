
## Creating test users
* start the dependend services `docker-compose -f deployment/docker-compose.yml up`
* generate users through httpie:
```bash
http --json -v\
  POST localhost:9000/api/v1/users\
  Service:auth\
  user:='{"username":"test", "first_name":"Test", "email":"test@xample.com"}'
```
