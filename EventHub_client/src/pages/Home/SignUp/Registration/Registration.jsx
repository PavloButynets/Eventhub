import styles from "./Registration.module.css";
import React, { useState, useContext } from "react";
import axios from "../../../../api/axios";
import { useNavigate, Link } from "react-router-dom";
import { PlacesAutocomplete } from "../../../../components/PlaceAutocomplete/PlaceAutocomplete";
import { checkEmail, checkName, checkPassword } from "./validation";
import CloseWindowButton from "../../../../components/Buttons/CloseWindowButton/CloseWindowButton";
import ProcessingEffect from "../../../../components/ProcessingEffect/ProcessingEffect";
import useAuth from "../../../../hooks/useAuth";
import AuthContext from "../../../../context/authProvider";

import { Button, Checkbox, Col, Form, Input, Row, Select, message } from "antd";

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

const Registration = ({ setUserEmail, setIsRegistered }) => {
  const [form] = Form.useForm();
  const [processing, setProcessing] = useState(false);
  const navigate = useNavigate();

  const [first_name, setFirstName] = useState("");
  const [last_name, setLastName] = useState("");
  const [password, setPassword] = useState("");
  const [username, setNickname] = useState("");
  const [gender, setGender] = useState("");
  const [city, setCity] = useState("");
  const [email, setEmail] = useState("");

  const { register } = useContext(AuthContext);

  const handleSubmit = async () => {
    const userData = {
      first_name,
      last_name,
      username,
      email,
      password,
      city,
      gender,
    };
    try {
      setProcessing(true);
      await register(userData);

      setIsRegistered(true);
      setUserEmail(userData.email);
    } catch (err) {
      if (!err.response) {
        message.error("No server response");
      } else {
        const status = err.response.status;
        message.error(err.response.data);
      }
    } finally {
      setProcessing(false);
    }
  };

  return (
    <>
      {processing && <ProcessingEffect />}
      <div className={styles.outerContainer}>
        <div className={styles.container}>
          <div className={styles.Buttons}>
            <CloseWindowButton onClick={() => navigate("/")} />
          </div>
          <Form
            className={styles.SignUpForm}
            {...formItemLayout}
            labelCol={{ span: 24 }} // Лейбл займає всю ширину
            wrapperCol={{ span: 24 }} // Поле вводу також займає всю ширину
            form={form}
            name="register"
            onFinish={handleSubmit}
            initialValues={{}}
            style={{
              maxWidth: 600,
            }}
            scrollToFirstError
          >
            <h1 style={{ margin: "15px 0px" }}>Register</h1>
            <Row gutter={16}>
              {" "}
              {/* Встановлюємо проміжок між елементами */}
              <Col xs={24} sm={12}>
                {" "}
                {/* Перший колонка, яка займає половину рядка */}
                <Form.Item
                  name="firstname"
                  label="First name"
                  className={styles.Item}
                  rules={[
                    {
                      required: true,
                      whitespace: true,
                      validator: checkName,
                    },
                  ]}
                >
                  <Input
                    placeholder="First name"
                    onChange={(e) => setFirstName(e.target.value)}
                  />
                </Form.Item>
              </Col>
              <Col xs={24} sm={12}>
                {" "}
                {/* Друга колонка, також займає половину рядка */}
                <Form.Item
                  className={styles.Item}
                  name="lastname"
                  label="Last name"
                  rules={[
                    {
                      required: true,
                      whitespace: true,
                      validator: checkName,
                    },
                  ]}
                >
                  <Input
                    placeholder="Last name"
                    onChange={(e) => setLastName(e.target.value)}
                  />
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
                  <Input.Password
                    onChange={(e) => setPassword(e.target.value)}
                  />
                </Form.Item>
              </Col>
              <Col xs={24} sm={12}>
                <Form.Item
                  name="confirm"
                  label="Confirm Password"
                  dependencies={["password"]}
                  hasFeedback
                  className={styles.Item}
                  rules={[
                    {
                      required: true,
                      message: "Please confirm your password!",
                    },
                    ({ getFieldValue }) => ({
                      validator(_, value) {
                        if (!value || getFieldValue("password") === value) {
                          return Promise.resolve();
                        }
                        return Promise.reject(
                          new Error("The new password do not match!")
                        );
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
                <Form.Item
                  name="nickname"
                  label="Nickname"
                  tooltip="What do you want others to call you?"
                  className={styles.Item}
                  rules={[
                    {
                      required: true,
                      message: "Please input your nickname!",
                      whitespace: true,
                    },
                  ]}
                >
                  <Input
                    onChange={(e) => setNickname(e.target.value)}
                    placeholder="Your Nick Name"
                  />
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
                      message: "Please select gender!",
                    },
                  ]}
                >
                  <Select
                    placeholder="select your gender"
                    onChange={(value) => setGender(value)}
                  >
                    <Option value="MALE">Male</Option>
                    <Option value="FEMALE">Female</Option>
                    <Option value="OTHER">Other</Option>
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
                      message: "Please select your City",
                      whitespace: true,
                    },
                  ]}
                >
                  <PlacesAutocomplete
                    onSelectLocation={(value) => setCity(value)}
                    initialValue={null}
                    onChange={(value) => setCity(value)}
                  />
                </Form.Item>
              </Col>
              <Col xs={24} sm={12}>
                <Form.Item
                  className={styles.Item}
                  name="email"
                  label="Email"
                  rules={[
                    {
                      required: true,
                      validator: checkEmail,
                      whitespace: true,
                    },
                  ]}
                >
                  <Input
                    style={{
                      width: "100%",
                    }}
                    onChange={(e) => setEmail(e.target.value)}
                  />
                </Form.Item>
              </Col>
            </Row>

            <Form.Item
              className={styles.Item}
              name="agreement"
              valuePropName="checked"
              rules={[
                {
                  validator: (_, value) =>
                    value
                      ? Promise.resolve()
                      : Promise.reject(new Error("Should accept agreement")),
                },
              ]}
            >
              <Checkbox>I agree to the processing of personal data</Checkbox>
            </Form.Item>
            <Form.Item {...tailFormItemLayout} className={styles.Item}>
              <Button
                type="primary"
                htmlType="submit"
                className={styles.customButton}
              >
                Register
              </Button>
              <span style={{ marginTop: "10px" }}>
                Already have an account? <Link to="/login">Login</Link>
              </span>
            </Form.Item>
          </Form>
        </div>
      </div>
    </>
  );
};

export default Registration;
