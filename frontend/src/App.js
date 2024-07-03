import React from 'react';
import { BrowserRouter as Router, Route, Routes, Link } from 'react-router-dom';
import { Layout, Menu } from 'antd';
import {
  DollarOutlined,
  LinkOutlined,
} from '@ant-design/icons';
import Payments from './components/Payments';
import Webhooks from './components/Webhooks';

const { Content, Sider } = Layout;

const App = () => {
  return (
    <Router>
      <Layout style={{ minHeight: '100vh' }}>
        <Sider collapsible>
          <Menu theme="dark" defaultSelectedKeys={['1']} mode="inline">
            <Menu.Item key="1" icon={<DollarOutlined />}>
              <Link to="/payments">Payments</Link>
            </Menu.Item>
            <Menu.Item key="2" icon={<LinkOutlined />}>
              <Link to="/webhooks">Webhooks</Link>
            </Menu.Item>
          </Menu>
        </Sider>
        <Layout>
          <Content>
            <Routes>
              <Route path="/payments" element={<Payments />} />
              <Route path="/webhooks" element={<Webhooks />} />
            </Routes>
          </Content>
        </Layout>
      </Layout>
    </Router>
  );
};

export default App;
