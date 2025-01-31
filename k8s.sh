cd ./k8s

cd db

cd driver-pg
kubectl apply -f driver-pg-database-data-persistentvolumeclaim.yaml
kubectl apply -f driver-pg-database-pod.yaml
kubectl apply -f driver-pg-database-service.yaml
cd ../

cd keycloak-pg
kubectl apply -f keycloak-pg-database-data-persistentvolumeclaim.yaml
kubectl apply -f keycloak-pg-database-pod.yaml
kubectl apply -f keycloak-pg-database-service.yaml
cd ../

cd passenger-pg
kubectl apply -f passenger-pg-database-data-persistentvolumeclaim.yaml
kubectl apply -f passenger-pg-database-pod.yaml
kubectl apply -f passenger-pg-database-service.yaml
cd ../

cd payment-pg
kubectl apply -f payment-pg-database-data-persistentvolumeclaim.yaml
kubectl apply -f payment-pg-database-pod.yaml
kubectl apply -f payment-pg-database-service.yaml
cd ../

cd payout-pg
kubectl apply -f payout-pg-database-data-persistentvolumeclaim.yaml
kubectl apply -f payout-pg-database-pod.yaml
kubectl apply -f payout-pg-database-service.yaml
cd ../

cd price-calculation-pg
kubectl apply -f price-calculation-pg-database-data-persistentvolumeclaim.yaml
kubectl apply -f price-calculation-pg-database-pod.yaml
kubectl apply -f price-calculation-pg-database-service.yaml
cd ../

cd promo-code-pg
kubectl apply -f promo-code-pg-database-data-persistentvolumeclaim.yaml
kubectl apply -f promo-code-pg-database-pod.yaml
kubectl apply -f promo-code-pg-database-service.yaml
cd ../

cd rating-mongo-1
kubectl apply -f rating-mongo-database1-data-persistentvolumeclaim.yaml
kubectl apply -f rating-mongo-database1-deployment.yaml
kubectl apply -f rating-mongo-database1-service.yaml
cd ../

cd rating-mongo-2
kubectl apply -f rating-mongo-database2-data-persistentvolumeclaim.yaml
kubectl apply -f rating-mongo-database2-deployment.yaml
kubectl apply -f rating-mongo-database2-service.yaml
cd ../

cd redis
kubectl apply -f cab-redis-data-persistentvolumeclaim.yaml
kubectl apply -f redis-pod.yaml
kubectl apply -f redis-service.yaml
cd ../

cd ride-mongo-1
kubectl apply -f ride-mongo-database1-data-persistentvolumeclaim.yaml
kubectl apply -f ride-mongo-database1-deployment.yaml
kubectl apply -f ride-mongo-database1-service.yaml
cd ../

cd ride-mongo-2
kubectl apply -f ride-mongo-database2-data-persistentvolumeclaim.yaml
kubectl apply -f ride-mongo-database2-deployment.yaml
kubectl apply -f ride-mongo-database2-service.yaml
cd ../

cd ../

cd elk

cd elasticsearch
kubectl apply -f elasticsearch-pod.yaml
kubectl apply -f elasticsearch-service.yaml
cd ../

cd kibana
kubectl apply -f kibana-pod.yaml
kubectl apply -f kibana-service.yaml
cd ../

cd logstash
kubectl apply -f logstash-cm0-configmap.yaml
kubectl apply -f logstash-pod.yaml
kubectl apply -f logstash-service.yaml
cd ../

cd ../

cd kafka
kubectl apply -f kafka-pod.yaml
kubectl apply -f kafka-service.yaml
cd ../

cd keycloak
kubectl apply -f keycloak-cm0-configmap.yaml
kubectl apply -f keycloak-pod.yaml
kubectl apply -f keycloak-service.yaml
cd ../

cd monitoring

cd grafana
kubectl apply -f grafana-pod.yaml
kubectl apply -f grafana-service.yaml
cd ../

cd prometheus
kubectl apply -f prometheus-cm0-configmap.yaml
kubectl apply -f prometheus-pod.yaml
kubectl apply -f prometheus-service.yaml
cd ../

cd zipkin
kubectl apply -f zipkin-pod.yaml
kubectl apply -f zipkin-service.yaml
cd ../

cd ../

cd services

cd api-gateway
kubectl apply -f api-gateway-pod.yaml
kubectl apply -f api-gateway-service.yaml
cd ../

cd auth-service
kubectl apply -f auth-service-pod.yaml
kubectl apply -f auth-service-service.yaml
cd ../

cd driver-service
kubectl apply -f driver-service-pod.yaml
kubectl apply -f driver-service-service.yaml
cd ../

cd eureka-server
kubectl apply -f eureka-server-pod.yaml
kubectl apply -f eureka-server-service.yaml
cd ../

cd passenger-service
kubectl apply -f passenger-service-pod.yaml
kubectl apply -f passenger-service-service.yaml
cd ../

cd payment-service
kubectl apply -f payment-service-pod.yaml
kubectl apply -f payment-service-service.yaml
cd ../

cd payout-service
kubectl apply -f payout-service-pod.yaml
kubectl apply -f payout-service-service.yaml
cd ../

cd price-calculation-service
kubectl apply -f price-calculation-service-pod.yaml
kubectl apply -f price-calculation-service-service.yaml
cd ../

cd promo-code-service
kubectl apply -f promo-code-service-pod.yaml
kubectl apply -f promo-code-service-service.yaml
cd ../

cd rating-service
kubectl apply -f rating-service-pod.yaml
kubectl apply -f rating-service-service.yaml
cd ../

cd ride-service
kubectl apply -f ride-service-pod.yaml
kubectl apply -f ride-service-service.yaml
cd ../

cd ../

cd zookeeper
kubectl apply -f zookeeper-pod.yaml
kubectl apply -f zookeeper-service.yaml
cd ../

cd ../
