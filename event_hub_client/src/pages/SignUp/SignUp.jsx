import styles from './SignUp.module.css';
import React, { useState } from 'react';
import axios from "axios";
import { useNavigate, Link } from 'react-router-dom';
import LogIn from '../LogIn/LogIn';
import {
  Button,
  Checkbox,
  Col,
  Form,
  Input,
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
const SignUp = () => {
  const [form] = Form.useForm();
  const navigate = useNavigate();


  const onFinish = (values) => {
    console.log('Received values of form: ', values);
  };

 
  const [firstName, setFirstName] = useState('');
  const [lastName, setLastName] = useState('');
  const [password, setPassword] = useState('');
  const [nickname, setNickname] = useState('');
  const [gender, setGender] = useState('');
  const [city, setCity] = useState('');
  const [email, setEmail] = useState('');

  const handleSubmit = async()=>{
     const userData = {
        firstName,
        lastName,
        password,
        nickname,
        gender,
        city,
        email,
     }
     try{
      const res = await axios.post('http://localhost:3500/users', userData);
      console.log(res)
      console.log('Response:', res.data);
      // navigate('/');

     }
     catch(err){
      console.log(err)
     }

  }


  return (
    <div className= {styles.RegisterPage}>
    <div className= {styles.container}>
    <img className={styles.Photo} src="/images/EventHub_loginPhoto.jpg" alt="Your Photo" />
    <Form className={styles.SignUpForm}
      {...formItemLayout}
      labelCol={{ span: 24 }}  // Лейбл займає всю ширину
      wrapperCol={{ span: 24 }} // Поле вводу також займає всю ширину
      form={form}
      name="register"
      onFinish={handleSubmit}
      initialValues={{

      }}
      style={{
        maxWidth: 600,
      }}
      scrollToFirstError
    >
  <h1 style={{margin:"0px"}}>Register</h1>
  <Row gutter={16}> {/* Встановлюємо проміжок між елементами */}
  <Col xs={24} sm={12}> {/* Перший колонка, яка займає половину рядка */}
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
      <Input placeholder="First name" onChange={(e) => setFirstName(e.target.value)}/>
    </Form.Item>
  </Col>
  <Col xs={24} sm={12}> {/* Друга колонка, також займає половину рядка */}
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
      <Input placeholder="Last name" onChange={(e)=>setLastName(e.target.value)} />
    </Form.Item>
  </Col>
</Row>
  <Row gutter={16}>
  <Col xs={24} sm={12}> 
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
        <Input.Password onChange={(e)=>setPassword(e.target.value)} />
      </Form.Item>
    </Col>
  <Col xs={24} sm={12}>
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
    <Row gutter={16}> 
  <Col xs={24} sm={12}> 
 
      <Form.Item name="nickname" label="Nickname" tooltip="What do you want others to call you?"   className={styles.Item}
        rules={[
          {
            required: true,
            message: 'Please input your nickname!',
            whitespace: true,
            
          },
        ]}
      >
        <Input onChange={(e)=>setNickname(e.target.value)} placeholder="Your Nick Name" />
      </Form.Item>
      </Col>
      <Col xs={24} sm={12}> 
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
        <Select placeholder="select your gender"
        onChange={(value) => setGender(value)}
        >
          <Option value="male">Male</Option>
          <Option value="female">Female</Option>
          <Option value="other">Other</Option>
        </Select>
      </Form.Item>
      </Col>
      </Row>
    <Row gutter={16}> 
  <Col xs={24} sm={12}>
 
      <Form.Item
        className={styles.Item}
        name="city"
        label="City"
        rules={[
          {
            required: true,
            message: 'Please select your City',
            whitespace: true,
          },
        ]}
      >
        <Input onChange={(e)=>setCity(e.target.value)}/>
      </Form.Item>
    </Col>
    <Col xs={24} sm={12}>
      <Form.Item  className={styles.Item}  name="email"  label="Email"
        rules={[
          {
            required: true,
            message: 'Please input your phone email!',
            whitespace: true,
          },
        ]}
      >
        <Input
          style={{
            width: '100%',
          } }
          onChange={(e)=>setEmail(e.target.value)}
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
        <Button  type="primary" htmlType="submit" className={styles.customButton}>
          Register
        </Button>
        <span style={{ marginTop: '10px' }}>
          Already have an account? <Link to="/LogIn">Login</Link>
        </span>
      </Form.Item>
    </Form>
    </div>
    </div>
  );
};

export default SignUp;