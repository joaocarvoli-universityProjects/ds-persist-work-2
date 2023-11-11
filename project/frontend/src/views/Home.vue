<script setup lang="ts">

import {Card, List} from "ant-design-vue";
</script>

<template>
  <div>
    <h1>Gerenciamento de Estoque</h1>
    <Card title="Opções disponíveis">
      <List>
        <router-link to="/manufacturer">
          <List.Item.Meta
              title="Fabricantes"
              avatar=""/>
        </router-link>
        <router-link to="/category">
          <List.Item.Meta
              title="Categorias de Produto"
              avatar=""
          />
        </router-link>
        <router-link to="/product">
          <List.Item.Meta
              title="Produtos"
              avatar=""
          />
        </router-link>
        <router-link to="/stock">
          <List.Item.Meta
              title="Estoque"
              avatar=""
          />
        </router-link>
        <router-link to="/product-request">
          <List.Item.Meta
              title="Solicitações de Produto"
              avatar=""
          />
        </router-link>
      </List>
    </Card>
  </div>
</template>