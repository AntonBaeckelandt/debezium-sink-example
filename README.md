# debezium-sink-example

This repository contains a Spring boot application which purpose is to calculate real time statistics. 

## Flow

1. Debezium monitors a database and pushes database changes to Kafka
2. This Spring boot application listens to the Kafka messages
2. This application calculate statistics for the current data
3. This application provides an HTTP endpoint and a SSE endpoint to make the statistics available for clients

## Front-end example

An example front-end that uses the statistics calculated in this project can be found [here](https://github.com/AntonBaeckelandt/real-time-analytics-react-example).
