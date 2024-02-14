import React from 'react';
import styles from './LogIn.module.css';
import { LockOutlined, UserOutlined } from '@ant-design/icons';
import { Button, Checkbox, Form, Input } from 'antd';
const LogIn = () => {
    const onFinish = (values) => {
        console.log('Received values of form: ', values);
    };
    return (

        <div className={styles.container}>
            <img className={styles.Photo} src="/images/EventHub_loginPhoto.jpg" alt="Your Photo"/>


            <Form

                name="normal_login"
                className="login-form"
                initialValues={{
                    remember: true,
                }}
                onFinish={onFinish}
            >
                <h1>Login</h1>
                <Form.Item
                    name="username"
                    rules={[
                        {
                            required: true,
                            message: 'Please input your Username!',
                        },
                    ]}
                >
                    <Input prefix={<UserOutlined className="site-form-item-icon"/>} placeholder="Username"/>
                </Form.Item>
                <Form.Item
                    name="password"
                    rules={[
                        {
                            required: true,
                            message: 'Please input your Password!',
                        },
                    ]}
                >
                    <Input.Password
                        prefix={<LockOutlined className="site-form-item-icon"/>}

                        type="password"
                        placeholder="Password"
                    />
                </Form.Item>

                <Form.Item>
                    <div className={styles.RememberMe}>
                        <Form.Item name="remember" valuePropName="checked" noStyle>
                            <Checkbox>Remember me</Checkbox>
                        </Form.Item>

                        <a className="login-form-forgot" href="">
                            Forgot password
                        </a>
                    </div>
                </Form.Item>


                <Form.Item>
                    <Button type="primary" htmlType="submit" className={styles.loginButton}>
                        LogIn
                    </Button>
                    <div>Or <a href="">register now!</a></div>
                </Form.Item>
            </Form>
        </div>
    );
};
export default LogIn;