### nab-icommerce
- Applying layers architect on each project: domain, repository, service, dto
- Using spring boot libraries: spring-jms, spring-mongodb, spring-jpa
- Using activemq, mysql, mongodb, nginx
- Using docker as running environment of services
<br/>
### Step to run on local machine
#### Install maven & docker before run below commands
- mvn install
- docker-compose build
- docker-compose up

### CURL for APIs
#### create product
curl -X POST -H "Content-Type: application/json" --data "{\"name\": \"Croccoli\", \"storeId\": 10,\"quantity\": 12,\"brand\": \"VinMart\",\"color\": \"Green\",\"price\": 12}" "localhost:8000/product-order-webservice/products"

### get product
curl "localhost:8000/product-order-webservice/products/1"

### search product
curl "localhost:8000/product-order-webservice/products?name=Croccoli&color=Green"

### update product
curl -X PUT -H "Content-Type: application/json" --data "{\"name\": \"Croccoli\", \"storeId\": 10,\"quantity\": 12,\"brand\": \"VinMart\",\"color\": \"Green\",\"price\": 12.2}" "localhost:8000/product-order-webservice/products/1" -i

### create shopping cart
curl -X POST -H "userId: 1" "localhost:8000/product-order-webservice/carts/create"

### add product to shopping cart
curl -X POST -H "userId: 1" -H "Content-Type: application/json" --data "{\"productId\": 1,\"quantity\": 2}" "localhost:8000/product-order-webservice/carts/add-product"

### get shopping cart
curl -H "userId: 1" "localhost:8000/product-order-webservice/carts"

### clear products in shopping cart
curl -X POST -H "userId: 1" "localhost:8000/product-order-webservice/carts/clear"

### place an order
curl -X POST -H "userId: 1" "localhost:8000/product-order-webservice/orders/create"

### update order status
curl -X POST -H "Content-Type: application/json" -H "userId: 1" --data "{\"id\": 1, \"status\": 1}" "localhost:8000/product-order-webservice/orders/update-status"

### get tracked prices of product
curl "localhost:8000/tracking-webservice/product-prices/1"
