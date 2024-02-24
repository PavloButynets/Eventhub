export const checkEmail = (_, value, callback) => {
    const reg = /^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{3})+$/;
    if (value.length === 0) {
      return callback('');
    }
    if (reg.test(value) === false) {
        return callback('Email is incorrect');
    }
      return callback();
  };



export const checkPassword = (_, value, callback) => {
    const reg = /^(?=.*\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[^a-zA-Z0-9])(?!.*\s).{8,}$/;
    if(value.length > 0)
    {
      if (value.length < 8)
      {
        return callback('Password should contain at least 8 characters');
      }

      if (reg.test(value) === false) 
      {
        return callback('Password should contain at least 1 uppercase letter, digit, and sign');
      }
    }
      return callback();
  };

  export const checkName = (_, value, callback) => {
    const reg = /^[a-zA-Zа-яА-ЯІіЄєЇїҐґ']{1,25}((\s+|-)[a-zA-Zа-яА-ЯІіЄєЇїҐґ']{1,25})*$/;
      if (value.length !== 0 && reg.test(value) === false) {
        return callback('First name and last name should contain only letters');
      }
      return callback();
  };


