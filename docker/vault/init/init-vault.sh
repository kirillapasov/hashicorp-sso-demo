#!/bin/sh

if ! vault secrets list -format=json | grep -q '"secret/"'; then
  vault secrets enable -path=secret kv-v2
fi

vault kv put secret/hashicorp-sso-demo app.example-secret="demo-value"
