import React from 'react';
import { shallow } from 'enzyme';
import Login from './login.component';

// Mock fetch function
global.fetch = jest.fn(() =>
  Promise.resolve({
    ok: true,
    json: () => Promise.resolve({ /* mock response data */ }),
  })
);

describe('Login component', () => {
  it('renders without crashing', () => {
    const wrapper = shallow(<Login />);
    expect(wrapper.exists()).toBe(true);
  });

  it('updates email state on input change', () => {
    const wrapper = shallow(<Login />);
    const emailInput = wrapper.find('input[type="email"]');
    emailInput.simulate('change', { target: { value: 'test@example.com' } });
    expect(wrapper.state('email')).toEqual('test@example.com');
  });

  it('updates password state on input change', () => {
    const wrapper = shallow(<Login />);
    const passwordInput = wrapper.find('input[type="password"]');
    passwordInput.simulate('change', { target: { value: 'password123' } });
    expect(wrapper.state('password')).toEqual('password123');
  });

});
