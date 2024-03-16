import styles from './SignUp.module.css';
import React, {useState } from 'react';
import axios from "../../api/axios";
import { useNavigate, Link } from 'react-router-dom';
import LogIn from '../LogIn/LogIn';
import { checkEmail, checkName, checkPassword } from './validation';
import useAuth from '../../hooks/useAuth';

import {
  Button,
  Checkbox,
  Col,
  Form,
  Input,
  Row,
  Select,
  message,
} from 'antd';



const { Option } = Select;
const REGISTER_URL = '/authentication/register'

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
  const {setAuth} = useAuth();
  const [form] = Form.useForm();
  const navigate = useNavigate();

 
  const [first_name, setFirstName] = useState('');
  const [last_name, setLastName] = useState('');
  const [password, setPassword] = useState('');
  const [username, setNickname] = useState('');
  const [gender, setGender] = useState('');
  const [city, setCity] = useState('');
  const [email, setEmail] = useState('');


  const handleSubmit = async()=>{
     const userData = {
      first_name ,
      last_name,
      username,
      email,
      password,
      description: "description 1",
      phone_number: "0162609247",
      city,
      birth_date: "1990-09-11",
      gender: "MALE"
  }
     try{
      
      const res = await axios.post(REGISTER_URL, userData, 
        {
          headers:{'Content-Type':'application/json'},
        }
      );
      const accessToken = res?.data?.token;
      localStorage.setItem("token", accessToken);
      console.log(res)
      console.log('Response:', res.data);
      message.success('Registration successful!');      
      navigate('/');

     }
     catch(err){
      if (!err.response) {
        // Помилка з'єднання з сервером
        message.error('No server response');
      } else {
        const status = err.response.status;
      switch (status) {
        case 400:
          // Помилка валідації даних на сервері
          message.error('Invalid data. Please check your input.');
          break;
        case 401:
          // Користувач не авторизований
          message.error('Unauthorized: Please check your credentials.');
          break;
        // case 403:
        //   // Доступ заборонено
        //   message.error('Forbidden: You do not have permission to access this resource.');
        //  break;
        case 404:
          // URL не знайдено
          message.error('Not Found: The requested resource was not found.');
          break;
        case 409:
          // Конфлікт
          message.error('Conflict: The resource already exists.');
          break;
        case 422:
          // Невірні вхідні дані
          message.error('Unprocessable Entity: The request was well-formed but unable to be followed due to semantic errors.');
          break;
        case 500:
          // Внутрішня помилка сервера
          message.error('Internal Server Error: Something went wrong on the server.');
          break;
        default:
          // Інші типи помилок
          console.error(err);
          message.error('Registration failed: ' + err.response.data);
          break;
      }
     }

   }
  
  }

  return (
    <div className= {styles.RegisterPage}>
    <div className= {styles.container}>
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
  <h1 style={{margin:"15px 0px"}}>Register</h1>
  <Row gutter={16}> {/* Встановлюємо проміжок між елементами */}
  <Col xs={24} sm={12}> {/* Перший колонка, яка займає половину рядка */}
    <Form.Item
      name="firstname"
      label="First name"
      className={styles.Item}
      rules={[
        {
          required: true,
          whitespace: true,
          validator: checkName
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
          whitespace: true,
          validator: checkName
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
            //validator: checkPassword,
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
            validator:checkEmail,
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
          Already have an account? <Link to="/login">Login</Link>
        </span>
      </Form.Item>
    </Form>
    </div>
    </div>
  );
};

export default SignUp;