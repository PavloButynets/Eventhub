import styles from './SignIn.module.css';
import React, { useState } from 'react';
import {
  AutoComplete,
  Button,
  Cascader,
  Checkbox,
  Col,
  Form,
  Input,
  InputNumber,
  Row,
  Select,
} from 'antd';


const { Option } = Select;

const formItemLayout = {
  labelCol: {
    xs: {
      span: 24,
    },
    sm: {
      span: 8,
    },
  },
  wrapperCol: {
    xs: {
      span: 24,
    },
    sm: {
      span: 16,
    },
  },
};
const tailFormItemLayout = {
  wrapperCol: {
    xs: {
      span: 24,
      offset: 0,
    },
    sm: {
      span: 16,
      offset: 8,
    },
  },
};
const App = () => {
  const [form] = Form.useForm();
  const onFinish = (values) => {
    console.log('Received values of form: ', values);
  };


  return (
    <div className= {styles.RegisterPage}>
    <div className= {styles.container}>
    <img className={styles.Photo} src="/images/EventHub_loginPhoto.jpg" alt="Your Photo" />
    <Form className={styles.SignInForm}
      {...formItemLayout}
      labelCol={{ span: 24 }}  // Лейбл займає всю ширину
      wrapperCol={{ span: 24 }} // Поле вводу також займає всю ширину
      form={form}
      name="register"
      onFinish={onFinish}
      initialValues={{
        city: "Lviv",
        prefix: '86',
      }}
      style={{
        maxWidth: 600,
      }}
      scrollToFirstError
    >
  <h1 style={{margin:"0px"}}>Register</h1>
  <Row gutter={16}> {/* Встановлюємо проміжок між елементами */}
  <Col span={12}> {/* Перший колонка, яка займає половину рядка */}
    <Form.Item
      name="firstname"
      label="First name"
      className={styles.Item}
      rules={[
        {
          required: true,
          message: 'Please input your First name!',
          whitespace: true,
        },
      ]}
    >
      <Input placeholder="First name" />
    </Form.Item>
  </Col>
  <Col span={12}> {/* Друга колонка, також займає половину рядка */}
    <Form.Item
      className={styles.Item}
      name="lastname"
      label="Last name"
      rules={[
        {
          required: true,
          message: 'Please input your Last name!',
          whitespace: true,
        },
      ]}
    >
      <Input placeholder="Last name" />
    </Form.Item>
  </Col>
</Row>
  <Row gutter={16}> {/* Встановлюємо проміжок між елементами */}
  <Col span={12}> {/* Перший колонка, яка займає половину рядка */}
      <Form.Item
        className={styles.Item}
        name="password"
        label="Password"
        rules={[
          {
            required: true,
            message: 'Please input your password!',
          },
        ]}
        hasFeedback
      >
        <Input.Password />
      </Form.Item>
    </Col>
  <Col span={12}>
      <Form.Item name="confirm" label="Confirm Password" dependencies={['password']} hasFeedback   className={styles.Item}
        rules={[
          {
            required: true,
            message: 'Please confirm your password!',
          },
          ({ getFieldValue }) => ({
            validator(_, value) {
              if (!value || getFieldValue('password') === value) {
                return Promise.resolve();
              }
              return Promise.reject(new Error('The new password that you entered do not match!'));
            },
          }),
        ]}
      >
        <Input.Password />
      </Form.Item>
      </Col>
    </Row>
    <Row gutter={16}> {/* Встановлюємо проміжок між елементами */}
  <Col span={12}> {/* Перший колонка, яка займає половину рядка */}
 
      <Form.Item name="nickname" label="Nickname" tooltip="What do you want others to call you?"   className={styles.Item}
        rules={[
          {
            required: true,
            message: 'Please input your nickname!',
            whitespace: true,
          },
        ]}
      >
        <Input />
      </Form.Item>
      </Col>
      <Col span={12}> 
      <Form.Item
        className={styles.Item}
        name="gender"
        label="Gender"
        rules={[
          {
            required: true,
            message: 'Please select gender!',
          },
        ]}
      >
        <Select placeholder="select your gender">
          <Option value="male">Male</Option>
          <Option value="female">Female</Option>
          <Option value="other">Other</Option>
        </Select>
      </Form.Item>
      </Col>
      </Row>
    <Row gutter={16}> {/* Встановлюємо проміжок між елементами */}
  <Col span={12}> {/* Перший колонка, яка займає половину рядка */}
 
      <Form.Item
        className={styles.Item}
        name="city"
        label="City"
        rules={[
          {
            required: true,
            message: 'Please select your City',
          },
        ]}
      >
        <Input/>
      </Form.Item>
    </Col>
    <Col span={12}>
      <Form.Item  className={styles.Item}  name="phone"  label="Phone Number"
        rules={[
          {
            required: true,
            message: 'Please input your phone number!',
          },
        ]}
      >
        <Input
          style={{
            width: '100%',
          }}
        />
      </Form.Item>
    </Col>
  </Row>


     <Form.Item
       className={styles.Item} name="agreement" valuePropName="checked"
        rules={[
          {
            validator: (_, value) =>
              value ? Promise.resolve() : Promise.reject(new Error('Should accept agreement')),
          },
        ]}
        
      >
        <Checkbox>
          I have read the <a  href="">agreement</a>
        </Checkbox>
      </Form.Item>
      <Form.Item {...tailFormItemLayout} className={styles.Item}>
        <Button type="primary" htmlType="submit" className={styles.customButton}>
          Register
        </Button>
        <span style={{ marginTop: '10px' }}>
          Already have an account? <a href="#">Login</a>
        </span>
      </Form.Item>
    </Form>
    </div>
    </div>
  );
};

export default App;