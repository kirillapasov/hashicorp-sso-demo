#!/bin/sh
set -e

vault server -dev -dev-root-token-id=root-token -dev-listen-address=0.0.0.0:8200 &

export VAULT_ADDR=http://127.0.0.1:8200

until vault status >/dev/null 2>&1; do
  sleep 1
done

export VAULT_TOKEN=root-token
sh /vault/init/init-vault.sh

wait
