<template>
  <h2>Produtos</h2>

<!--   Product Table -->
  <Table :columns="columns_product" :data-source="productItems" bordered>
    <template #bodyCell="{ column, text, record }">
      <template v-if="['name', 'amount', 'price'].includes(column.dataIndex)">
        <div>
          <Input
              v-if="editableData[record.id]"
              v-model:value="editableData[record.id][column.dataIndex]"
              style="margin: -5px 0"
          />
          <template v-else>
            {{ text }}
          </template>
        </div>
      </template>

      <template v-if="['manufacturingDate', 'expirationDate'].includes(column.dataIndex)">
        <div>
          <Input
              v-if="editableData[record.id]"
              type="date"
              v-model:value="editableData[record.id][column.dataIndex]"
              style="margin: -5px 0"
          />
          <template v-else>
            {{ text }}
          </template>
        </div>
      </template>



      <template v-else-if="column.dataIndex === 'manufacturer'">
        <div>
          <a-select
              v-if="editableData[record.id]"
              v-model:value="editableData[record.id][column.dataIndex].name"
              style="width: 120px"
              :options="manufacturerItems.map(item => ({ value: item.id, label: item.name }))"
          ></a-select>
          <template v-else>
            {{ record.manufacturer.name }}
          </template>
        </div>
      </template>

      <template v-else-if="column.dataIndex === 'category'">
        <div>
          <a-select
              v-if="editableData[record.id]"
              v-model:value="editableData[record.id][column.dataIndex].name"
              style="width: 120px"
              :options="categoryItems.map(item => ({ value: item.id, label: item.name }))"
          ></a-select>
          <template v-else>
            {{ record.category.name }}
          </template>
        </div>
      </template>

      <template v-else-if="column.dataIndex === 'operation'">
        <div class="editable-row-operations">
            <span v-if="editableData[record.id]">
              <Typography.Link @click="save(record.id)">Save</Typography.Link>
              <Popconfirm title="Sure to cancel?" @confirm="cancel(record.id)">
                <a>Cancel</a>
              </Popconfirm>
            </span>
          <span v-else class="editable_operations">
              <a @click="edit(record.id)"><EditOutlined /></a>
              <Popconfirm title="Sure to delete?" @confirm="removeProductById(record.id)">
                <a><DeleteOutlined /></a>
              </Popconfirm>
            </span>
        </div>
      </template>


    </template>
  </Table>

  <!-- Floating Button and Modal -->
  <div id="components-modal-demo-position">
    <a-float-button @click="setModalVisible(true)">
      <template #tooltip>
        <div>Display Modal</div>
      </template>
      <template #icon>
        <PlusOutlined />
      </template>
    </a-float-button>

    <a-modal v-model:open="modalVisible" title="Product Registration" centered @ok="handleOkButtonModal" width="600px">
      <a-form>
        <a-form-item
            label="Name"
            name="name">
          <a-input v-model:value="formStateManufacturer.name" />
        </a-form-item>
        <div class="dates">
          <a-form-item
              label="Data de Fabricação"
              name="name">
            <a-input type="date" v-model:value="formStateManufacturer.manufacturingDate" />
          </a-form-item>
          <a-form-item
              label="Data de Validade"
              name="name"
          >
            <a-input type="date" v-model:value="formStateManufacturer.expirationDate"/>
          </a-form-item>
        </div>
        <div class="dates">
          <a-form-item
              label="Fabricante"
              name="name">
            <a-select
                v-model:value="formStateManufacturer.manufacturerId"
                style="width: 120px"
                :options="productItems.map(item => ({ value: item.id, label: item.name }))"
            ></a-select>
          </a-form-item>
          <a-form-item
              label="Categoria do Produto"
              name="name"
          >
            <a-select
                v-model:value="formStateManufacturer.categoryId"
                style="width: 120px"
                :options="categoryItems.map(item => ({ value: item.id, label: item.name }))"
            ></a-select>
          </a-form-item>
        </div>
        <div class="dates">
          <a-form-item
              label="Quantidade de items"
              name="name"
          >
            <a-input v-model:value="formStateManufacturer.amount"/>
          </a-form-item>

          <a-form-item
              label="Valor"
              name="amount"
          >
            <a-input v-model:value="formStateManufacturer.price"/>
          </a-form-item>
        </div>
      </a-form>
    </a-modal>
  </div>
</template>

<script setup lang="ts">
import {onBeforeMount, reactive, ref, UnwrapRef} from "vue";
import { Table, Input, Typography, Popconfirm } from "ant-design-vue";
import { Manufacturer } from "../model/manufacturer.ts";
import { EditOutlined, DeleteOutlined, PlusOutlined } from "@ant-design/icons-vue";
import { columns_product} from "../utils/tablesCols.ts";
import {useProductStore} from "../stores/productStore.ts";
import {ProductDto} from "./dto/productDto.ts";
import {mapToProductDto} from "../model/mappers.ts";
import {Product} from "../model/product.ts";
import {Category} from "../model/category.ts";
import {useCategoryStore} from "../stores/categoryStore.ts";
import {useManufacturerStore} from "../stores/manufacturerStore.ts";

const productStore = useProductStore();
const categoryStore = useCategoryStore()
const manufacturerStore = useManufacturerStore()

const productItems = ref<Product[]>([]);
const manufacturerItems = ref<Manufacturer[]>([])
const categoryItems = ref<Category[]>([]);

const modalVisible = ref<boolean>(false);

async function getAllCategories() {
  const result = await categoryStore.getAllCategories();
  categoryItems.value = result;
}

async function getAllManufacturers() {
  const result = await manufacturerStore.getAllManufacturers();
  manufacturerItems.value = result;
}

async function getAllProducts() {
  const result = await productStore.getAllProducts();
  productItems.value = result;
}

async function createProduct(){
  const response = await productStore.createProduct(formStateManufacturer)
  if(response.success) await getAllProducts()
}

async function editProductById(editableData: Record<string, Partial<Product>>){
  console.log("Calling function edit")
  const product = (editableData as unknown) as Product
  const response = await productStore.editProductById(product.id, mapToProductDto(product));
  if(response.success) await getAllProducts()
}

async function removeProductById(manufacturerIdData: string) {
  console.log("Calling function remove")

  const manufacturerId = parseInt(manufacturerIdData);
  const response = await productStore.removeProductById(manufacturerId);
  if(response.success) await getAllProducts()
}

onBeforeMount(() => {
  getAllManufacturers();
  getAllProducts();
  getAllCategories();
});

const editableData: UnwrapRef<Record<string, Partial<Product>>> = reactive({});

const edit = (key: string) => {
  editableData[parseInt(key)] = { ...productItems.value.find((item) => parseInt(key) === item.id) };
};

const save = (key: string) => {
  Object.assign(
      productItems.value.find(
          (item) => parseInt(key) === item.id
      ),
      editableData[key]
  );
  editProductById(editableData[parseInt(key)])
  delete editableData[key];
};

const cancel = (key: string) => {
  delete editableData[parseInt(key)];
};

const setModalVisible = (open: boolean) => {
  modalVisible.value = open;
};

const formStateManufacturer = reactive<ProductDto>({
  name: '',
  manufacturerId: null,
  manufacturingDate: new Date(),
  categoryId: null,
  expirationDate: new Date(),
  amount: 0,
  price: 0
});



const handleOkButtonModal = () => {
  createProduct()
  setModalVisible(false)
}

</script>

<style scoped>
.editable-row-operations {
  display: flex;
  justify-content: space-around;
}

.editable-row-operations a {
  margin-right: 8px;
}

.editable_operations {
  display: flex;
}

.dates {
  display: flex;
  gap: 10px;
}
</style>
