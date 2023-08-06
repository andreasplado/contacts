### Kontaktid

# Käivita: 
On vaja angular clid

``` npm install -g @angular/cli```

Installi paketid
```npm install```

Jooksuta server
```ng serve```




# Uuenda või loo olemid koodiga
change application.properties
```spring.jpa.hibernate.ddl-auto=create```

# Otspunktid

## Kontakt 

GET```https://localhost:8080/contact/all```


GET```https://localhost:8080/contact/{ID}```

POST``` https://localhost:8080/contact/```
```
{
	"real_name": "Andreas Plado",
	"secret_code": "Salakood",
	"phone" : "+37258802867"
}
```
PUT``` https://localhost:8080/contact/{ID}```

DELETE``` https://localhost:8080/contact/{ID}```

DELETE``` https://localhost:8080/contact/deleteall```

## Klient

GET```https://localhost:8080/client/all```

GET```https://localhost:8080/client/{ID}```

POST``` https://localhost:8080/client/```
```
{
	"real_name": "Andreas Plado",
	"secret_code": "Salakood",
	"phone" : "+37258802867"
}
```
PUT``` https://localhost:8080/client/{ID}```

DELETE``` https://localhost:8080/client/{ID}```

DELETE``` https://localhost:8080/client/deleteall```


## Vastused
Vastuses on kas postitatud vorm või JSON objekt ResponseModel



ResponseModel:
```
{
	"message": "Teade"
}
```

