import http from 'k6/http';
import { check } from 'k6';

export const options = {
  vus: 5,
  duration: '2m'
};

export default function () {
  const email = encodeURIComponent(__ENV.TEST_EMAIL || '');
  const senha = encodeURIComponent(__ENV.TEST_PASSWORD || '');

  const url = `http://host.docker.internal:8081/auth/login?email=${email}&senha=${senha}`;

  const response = http.post(url);

  check(response, {
    'login HTTP 200': (r) => r.status === 200
  });
}
