<script setup lang="ts">

import {Card, List} from "ant-design-vue";
</script>

<template>
  <div>
    <Card title="Options">
      <List>
        <router-link to="/manufacturer">
          <List.Item.Meta
              title="Manufacturers"
              avatar=""/>
        </router-link>
        <router-link to="/category">
          <List.Item.Meta
              title="Categories"
              avatar=""
          />
        </router-link>
        <router-link to="/product">
          <List.Item.Meta
              title="Product"
              avatar=""
          />
        </router-link>
        <router-link to="/stock">
          <List.Item.Meta
              title="Stock"
              avatar=""
          />
        </router-link>
        <router-link to="/product-request">
          <List.Item.Meta
              title="Product Request"
              avatar=""
          />
        </router-link>
      </List>
    </Card>
  </div>
</template>