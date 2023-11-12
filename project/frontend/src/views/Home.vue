<script setup lang="ts">
</script>

<template>
  <div>
    <h1>Gerenciamento de Estoque</h1>
    <a-card title="Opções disponíveis" >
      <a-list class="items-list">
        <router-link to="/product">
          <a-list-item-meta class="item-option">
            <template #title>
              <a-button>
                Produtos
              </a-button>
            </template>
          </a-list-item-meta>
        </router-link>

        <router-link to="/stock">
          <a-list-item-meta class="item-option">
            <template #title>
              <a-button>
                Depósitos
              </a-button>
            </template>
          </a-list-item-meta>
        </router-link>

        <router-link to="/category">
          <a-list-item-meta class="item-option">
            <template #title>
              <a-button>
                Categorias
              </a-button>
            </template>
          </a-list-item-meta>
        </router-link>

        <router-link to="/manufacturer">
          <a-list-item-meta class="item-option">
            <template #title>
              <a-button>
                Fabricantes
              </a-button>
            </template>
          </a-list-item-meta>
        </router-link>

      </a-list>
    </a-card>
  </div>
</template>

<style>
.items-list {
  display: flex;
  justify-content: center;
  flex-direction: row;
}

.item-option {
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 20px;
}
</style>