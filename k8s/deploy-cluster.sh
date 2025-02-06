cd db

cd driver-pg
kubectl apply -f driver-pg-database-data-persistentvolumeclaim.yaml
kubectl apply -f driver-pg-database-configmap.yaml
kubectl apply -f driver-pg-database.yaml
cd ../

cd keycloak-pg
kubectl apply -f keycloak-pg-database-data-persistentvolumeclaim.yaml
kubectl apply -f keycloak-pg-database-configmap.yaml
kubectl apply -f keycloak-pg-database.yaml
cd ../

cd passenger-pg
kubectl apply -f passenger-pg-database-data-persistentvolumeclaim.yaml
kubectl apply -f passenger-pg-database-configmap.yaml
kubectl apply -f passenger-pg-database.yaml
cd ../

cd payment-pg
kubectl apply -f payment-pg-database-data-persistentvolumeclaim.yaml
kubectl apply -f payment-pg-database-configmap.yaml
kubectl apply -f payment-pg-database.yaml
cd ../

cd payout-pg
kubectl apply -f payout-pg-database-data-persistentvolumeclaim.yaml
kubectl apply -f payout-pg-database-configmap.yaml
kubectl apply -f payout-pg-database.yaml
cd ../

cd price-calculation-pg
kubectl apply -f price-calculation-pg-database-data-persistentvolumeclaim.yaml
kubectl apply -f price-calculation-pg-database-configmap.yaml
kubectl apply -f price-calculation-pg-database.yaml
cd ../

cd promo-code-pg
kubectl apply -f promo-code-pg-database-data-persistentvolumeclaim.yaml
kubectl apply -f promo-code-pg-database-configmap.yaml
kubectl apply -f promo-code-pg-database.yaml
cd ../

cd rating-mongo-1
kubectl apply -f rating-mongo-database1-data-persistentvolumeclaim.yaml
kubectl apply -f rating-mongo-database1.yaml
cd ../

cd rating-mongo-2
kubectl apply -f rating-mongo-database2-data-persistentvolumeclaim.yaml
kubectl apply -f rating-mongo-database2.yaml
cd ../

cd redis
kubectl apply -f redis-data-persistentvolumeclaim.yaml
kubectl apply -f redis-configmap.yaml
kubectl apply -f redis.yaml
cd ../

cd ride-mongo-1
kubectl apply -f ride-mongo-database1-data-persistentvolumeclaim.yaml
kubectl apply -f ride-mongo-database1.yaml
cd ../

cd ride-mongo-2
kubectl apply -f ride-mongo-database2-data-persistentvolumeclaim.yaml
kubectl apply -f ride-mongo-database2.yaml
cd ../

cd ../

cd elk

cd elasticsearch
kubectl apply -f elasticsearch-configmap.yaml
kubectl apply -f elasticsearch.yaml
cd ../

cd kibana
kubectl apply -f kibana-configmap.yaml
kubectl apply -f kibana.yaml
cd ../

cd logstash
kubectl apply -f logstash-init-config-configmap.yaml
kubectl apply -f logstash-configmap.yaml
kubectl apply -f logstash.yaml
cd ../

cd ../

cd kafka
kubectl apply -f kafka-configmap.yaml
kubectl apply -f kafka.yaml
cd ../

cd keycloak
kubectl apply -f keycloak-export-realm-configmap.yaml
kubectl apply -f keycloak-configmap.yaml
kubectl apply -f keycloak.yaml
cd ../

cd monitoring

cd grafana
kubectl apply -f grafana-datasource-configmap.yaml
kubectl apply -f grafana-dashboard-configmap.yaml
kubectl apply -f grafana-configmap.yaml
kubectl apply -f grafana.yaml
cd ../

cd prometheus
kubectl apply -f prometheus-init-config-configmap.yaml
kubectl apply -f prometheus.yaml
cd ../

cd zipkin
kubectl apply -f zipkin.yaml
cd ../

cd ../

cd services

cd api-gateway
kubectl apply -f api-gateway-configmap.yaml
kubectl apply -f api-gateway.yaml
cd ../

cd auth-service
kubectl apply -f auth-service-configmap.yaml
kubectl apply -f auth-service.yaml
cd ../

cd driver-service
kubectl apply -f driver-service-configmap.yaml
kubectl apply -f driver-service.yaml
cd ../

cd eureka-server
kubectl apply -f eureka-server-configmap.yaml
kubectl apply -f eureka-server.yaml
cd ../

cd passenger-service
kubectl apply -f passenger-service-configmap.yaml
kubectl apply -f passenger-service.yaml
cd ../

cd payment-service
kubectl apply -f payment-service-configmap.yaml
kubectl apply -f payment-service.yaml
cd ../

cd payout-service
kubectl apply -f payout-service-configmap.yaml
kubectl apply -f payout-service.yaml
cd ../

cd price-calculation-service
kubectl apply -f price-calculation-service-configmap.yaml
kubectl apply -f price-calculation-service.yaml
cd ../

cd promo-code-service
kubectl apply -f promo-code-service-configmap.yaml
kubectl apply -f promo-code-service.yaml
cd ../

cd rating-service
kubectl apply -f rating-service-configmap.yaml
kubectl apply -f rating-service.yaml
cd ../

cd ride-service
kubectl apply -f ride-service-configmap.yaml
kubectl apply -f ride-service.yaml
cd ../

cd ../

cd zookeeper
kubectl apply -f zookeeper.yaml
cd ../
