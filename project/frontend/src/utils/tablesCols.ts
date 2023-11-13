export const columns_manufacturer = [
    {
        title: "Nome",
        dataIndex: "name",
        key: "name",
        customFilterDropdown: true,
        sorter: true,
    },
    {
        title: "Operações",
        dataIndex: "operation",
        width: '10%'
    },
];

export const columns_category = [
    {
        title: "Nome",
        dataIndex: "name",
        key: "name",
        customFilterDropdown: true,
        sorter: true,
    },
    {
        title: "Operações",
        dataIndex: "operation",
        width: '10%'
    },
];

export const columns_stock = [
    {
        title: "Nome",
        dataIndex: "name",
        key: "name",
        customFilterDropdown: true,
        sorter: true,
    },
    {
        title: "Endereço",
        dataIndex: "address",
        key: "address",
        customFilterDropdown: true,
        sorter: true,
    },
    {
        title: "CEP",
        dataIndex: "cep",
        key: "cep",
        customFilterDropdown: true,
        sorter: true,
    },
    {
        title: "Operações",
        dataIndex: "operation",
        width: '15%'
    },
];

export const columns_product = [
    {
        title: "Nome",
        dataIndex: "name",
        key: "name",
        customFilterDropdown: true,
        sorter: true,
    },
    {
        title: "Preço",
        dataIndex: "price",
        key: "price",
        customFilterDropdown: true,
        sorter: true,
    },
    {
        title: "Quantidade",
        dataIndex: "amount",
        key: "amount",
        customFilterDropdown: true,
        sorter: true,
    },
    {
        title: "Fabricante",
        dataIndex: "manufacturer",
        key: "manufacturer",
        customFilterDropdown: true,
        sorter: true,
    },
    {
        title: "Data de fabricação",
        dataIndex: "manufacturingDate",
        key: "manufacturingDate",
        // customFilterDropdown: true,
    },
    {
        title: "Data de vencimento",
        dataIndex: "expirationDate",
        key: "expirationDate",
        // customFilterDropdown: true,
    },
    {
        title: "Categoria do Produto",
        dataIndex: "category",
        key: "category",
        customFilterDropdown: true,
        sorter: true,
    },
    {
        title: "Nome Depósito",
        dataIndex: "stock",
        key: "stock",
        customFilterDropdown: true,
        sorter: true,
    },
    {
        title: "Operações",
        dataIndex: "operation",
        width: '10%'
    },
];

export const filter_options_stock = [
    {
        name:'Nome',
        req: 'name',
        key:'name'
    },
    {
        name:'Endereço',
        req: 'address',
        key:'address'
    },
    {
        name:'CEP',
        req: 'cep',
        key:'cep'
    }
]

export const filter_options_manufacturer = [
    {
        name:'Nome',
        req: 'name',
        key:'name'
    },
]

export const filter_options_categories = [
    {
        name:'Nome',
        req: 'name',
        key:'name'
    },
]

export const filter_options_products = [
    {
        name:'Nome',
        req: 'Name',
        key:'name'
    },
    {
        name:'Fabricante',
        req: 'ManufacturerName',
        key:'manufacturer'
    },
    {
        name:'Categoria',
        req: 'CategoryName',
        key:'category'
    },
    {
        name:'Depósito',
        req: 'StockName',
        key:'stock'
    },
    {
        name:'Preço',
        req: 'Price',
        key:'price'
    },
    {
        name:'Quantidade',
        req: 'Amount',
        key:'amount'
    }
]