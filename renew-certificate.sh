#! /bin/bash

sudo docker run --rm --name certbot_for_renewal \
-v './certbot/conf:/etc/letsencrypt' \
-v './certbot/logs:/var/log/letsencrypt' \
-v './certbot/lib:/var/lib/letsencrypt' \
-v './certbot/www:/var/www/certbot' \
certbot/certbot certonly -n --expand --webroot --force-renewal --webroot-path=/var/www/certbot --agree-tos -d "psythinktank.com,www.psythinktank.com,m.psythinktank.com"

sudo docker exec nginx nginx -s reload
