<script setup lang="ts">

import {Card, List} from "ant-design-vue";
</script>

<template>
  <div>
    <h1>Stock Management</h1>
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
              title="Products"
              avatar=""
          />
        </router-link>
        <router-link to="/stock">
          <List.Item.Meta
              title="Stocks"
              avatar=""
          />
        </router-link>
        <router-link to="/product-request">
          <List.Item.Meta
              title="Products Requests"
              avatar=""
          />
        </router-link>
      </List>
    </Card>
  </div>
</template>