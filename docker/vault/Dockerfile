FROM hashicorp/vault:1.17

COPY init/init-vault.sh /vault/init/init-vault.sh
COPY start-vault.sh /usr/local/bin/start-vault.sh

RUN chmod +x /vault/init/init-vault.sh /usr/local/bin/start-vault.sh

ENTRYPOINT ["/usr/local/bin/start-vault.sh"]
