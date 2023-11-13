<template>
  <h2>Produtos</h2>

<!--   Product Table -->
  <div class="table-section">

    <a-table :columns="columns_product" :data-source="productItems" bordered @change="handleChange">
      <template
          #customFilterDropdown="{ setSelectedKeys, selectedKeys, confirm, clearFilters, column }"
      >
        <template v-if="['name', 'manufacturer', 'category', 'stock'].includes(column.dataIndex)">
          <div style="padding: 8px">
            <a-input
                ref="searchInput"
                :placeholder="`Buscar por ${filter_options_products.filter((item) => item.key == column.dataIndex)[0].name}`"
                :value="selectedKeys[0]"
                style="width: 188px; margin-bottom: 8px; display: block"
                @change="e => setSelectedKeys(e.target.value ? [e.target.value] : [])"
                @pressEnter="handleSearch(selectedKeys, confirm, column.dataIndex)"
            />
            <a-button
                type="primary"
                size="small"
                style="width: 90px; margin-right: 8px"
                @click="handleSearch(selectedKeys, confirm, column.dataIndex)"
            >
              <template #icon><SearchOutlined /></template>
              Search
            </a-button>
            <a-button size="small" style="width: 90px" @click="handleReset(clearFilters)">
              Reset
            </a-button>
          </div>
        </template>

        <template v-else-if="['price', 'amount'].includes(column.dataIndex)">
          <div style="padding: 8px">
            <a-input
                ref="searchInput"
                :placeholder="`Buscar por ${filter_options_products.filter((item) => item.key == column.dataIndex)[0].name}.`"
                :value="selectedKeys[0]"
                style="width: 188px; margin-bottom: 8px; display: block"
                @change="e => setSelectedKeys(e.target.value ? [e.target.value] : [])"
                @pressEnter="handleSearch(selectedKeys, confirm, column.dataIndex)"
            />
            <a-button
                type="primary"
                size="small"
                style="width: 90px; margin-right: 8px"
                @click="handleSearch(selectedKeys, confirm, column.dataIndex)"
            >
              <template #icon><SearchOutlined /></template>
              Search
            </a-button>
            <a-button size="small" style="width: 90px" @click="handleReset(clearFilters)">
              Reset
            </a-button>
          </div>
        </template>

        <template v-else>
          <!-- Date picker for other columns -->
          <div style="padding: 8px">
            <a-range-picker
                ref="searchInput"
                :placeholder="`Search ${filter_options_products.filter((item) => item.key === column.dataIndex)}`"
                :value="selectedKeys[0]"
                style="width: 300px; margin-bottom: 8px; display: block"
                @change="date => setSelectedKeys(date ? [date] : [])"
                @pressEnter="handleSearch(selectedKeys, confirm, column.dataIndex)"
            />

            <a-button
                type="primary"
                size="small"
                style="width: 90px; margin-right: 8px"
                @click="handleSearch(selectedKeys, confirm, column.dataIndex)"
            >
              <template #icon><SearchOutlined /> /></template>
              Search
            </a-button>
            <a-button size="small" style="width: 90px" @click="handleReset(clearFilters)">
              Reset
            </a-button>
          </div>
        </template>
      </template>


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

        <template v-else-if="column.dataIndex === 'stock'">
          <div>
            <a-select
                v-if="editableData[record.id]"
                v-model:value="editableData[record.id][column.dataIndex].name"
                style="width: 120px"
                :options="stockItems.map(item => ({ value: item.id, label: item.name }))"
            ></a-select>
            <template v-else>
              {{ record.stock.name }}
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
    </a-table>
  </div>


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

    <a-modal v-model:open="modalVisible" title="Cadastrar Produto" centered @ok="handleOkButtonModal" width="600px">
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
                :options="manufacturerItems.map(item => ({ value: item.id, label: item.name }))"
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
        <a-form-item
            label="Categoria do Produto"
            name="name"
        >
          <a-select
              v-model:value="formStateManufacturer.stockId"
              style="width: 120px"
              :options="stockItems.map(item => ({ value: item.id, label: item.name }))"
          ></a-select>
        </a-form-item>
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
import { Input, Typography, Popconfirm, TableProps} from "ant-design-vue";
import { Manufacturer } from "../model/manufacturer.ts";
import { EditOutlined, DeleteOutlined, PlusOutlined, SearchOutlined } from "@ant-design/icons-vue";
import {columns_product, filter_options_products} from "../utils/tablesCols.ts";
import {useProductStore} from "../stores/productStore.ts";
import {ProductDto} from "./dto/productDto.ts";
import {mapToProductDto} from "../model/mappers.ts";
import {Product} from "../model/product.ts";
import {Category} from "../model/category.ts";
import {useCategoryStore} from "../stores/categoryStore.ts";
import {useManufacturerStore} from "../stores/manufacturerStore.ts";
import {useStockStore} from "../stores/stockStore.ts";
import {Stock} from "../model/stock.ts";

const productStore = useProductStore();
const categoryStore = useCategoryStore()
const manufacturerStore = useManufacturerStore()
const stockStore = useStockStore()

const productItems = ref<Product[]>([]);
const manufacturerItems = ref<Manufacturer[]>([])
const categoryItems = ref<Category[]>([]);
const stockItems = ref<Stock[]>([])

const modalVisible = ref<boolean>(false);

async function getAllCategories() {
  const result = await categoryStore.getAllCategories();
  categoryItems.value = result;
}

async function getAllProductsByFieldName(field: String, name: String){
  const result = await productStore.getAllByName(field, name);
  productItems.value = result;
}

async function getAllProductsByPrice(price: number){
  const result = await productStore.getAllByPrice(price);
  productItems.value = result;
}

async function getAllProductsByPriceRange(priceInitial: number, priceFinal: number){
  const result = await productStore.getAllByPriceRange(priceInitial, priceFinal);
  productItems.value = result;
}

async function getAllProductsByAmount(amount: number){
  const result = await productStore.getAllByAmount(amount);
  productItems.value = result;
}

async function getAllProductsByAmountRange(amountInitial: number, amountFinal: number){
  const result = await productStore.getAllByAmountRange(amountInitial, amountFinal);
  productItems.value = result;
}

async function getAllManufacturers() {
  const result = await manufacturerStore.getAllManufacturers();
  manufacturerItems.value = result;
}

async function getAllStocks() {
  const result = await stockStore.getAllStocks();
  stockItems.value = result;
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
  const product = (editableData as unknown) as Product
  const response = await productStore.editProductById(product.id, mapToProductDto(product));
  if(response.success) await getAllProducts()
}

async function removeProductById(manufacturerIdData: string) {
  const manufacturerId = parseInt(manufacturerIdData);
  const response = await productStore.removeProductById(manufacturerId);
  if(response.success) await getAllProducts()
}

async function orderByField(field: String, direction: String){
  const result = await productStore.orderByField(field, direction);
  productItems.value = result;
}

onBeforeMount(() => {
  getAllManufacturers();
  getAllProducts();
  getAllCategories();
  getAllStocks();
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
  price: 0,
  stockId: null,
});

const state = reactive({
  searchText: '',
  searchedColumn: '',
});

const searchInput = ref();

const handleSearch = (selectedKeys, confirm, dataIndex) => {
  confirm();
  state.searchText = selectedKeys[0];
  state.searchedColumn = dataIndex;
  switch (dataIndex){
    case 'manufacturer':
    case 'category':
    case 'stock':
    case 'name':
      const field = filter_options_products.filter((item) => item.key == dataIndex)[0].req
      getAllProductsByFieldName(field, selectedKeys[0])
      break
    case 'price':
      const data = selectedKeys[0].split(':')

      if(data.length == 1){
        getAllProductsByPrice(data[0])
      } else {
        getAllProductsByPriceRange(data[0], data[1])
      }
      break
    case 'amount':
      const data2 = selectedKeys[0].split(':')

      if(data2.length == 1){
        getAllProductsByAmount(data2[0])
      } else {
        getAllProductsByAmountRange(data2[0], data2[1])
      }
      break
  }
};

const handleChange: TableProps['onChange'] = (pagination, filters, sorter) => {
  let field = sorter['columnKey']
  let order = sorter['order'].split('end')[0]

  orderByField(field, order)
};

const handleReset = clearFilters => {
  clearFilters({ confirm: true });
  state.searchText = '';
  getAllProducts()
};

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

.table-section {
  flex-direction: column;
  display: flex;
  gap: 15px;
}

.search-section {
  flex-direction: row;
  display: flex;
  gap: 15px;
}

</style>
