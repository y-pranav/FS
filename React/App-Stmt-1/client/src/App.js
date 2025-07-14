import React from 'react';
import ProductTable from './ProductTable';
import './App.css';

function App() {
  return (
    <div className="App">
      <h2 style={{textAlign: 'center', marginTop: '32px', color: '#1976d2'}}>Product Catalog</h2>
      <ProductTable />
    </div>
  );
}

export default App;
