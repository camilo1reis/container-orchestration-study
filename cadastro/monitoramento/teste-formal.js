import http from 'k6/http';
import { check } from 'k6';

let failuresLogged = 0;

export const options = {
  vus: Number(__ENV.VUS || 5),
  duration: __ENV.DURATION || '2m'
};

export default function () {
  const email = encodeURIComponent(__ENV.TEST_EMAIL || '');
  const senha = encodeURIComponent(__ENV.TEST_PASSWORD || '');
  const baseUrl = __ENV.BASE_URL || 'http://host.docker.internal:8081';

  const response = http.post(
    `${baseUrl}/auth/login?email=${email}&senha=${senha}`
  );

  if (response.status !== 200 && failuresLogged < 10) {
    console.error(
      `FALHA_LOGIN status=${response.status} ` +
      `error=${response.error || 'sem detalhe'} ` +
      `duracao_ms=${response.timings.duration}`
    );
    failuresLogged++;
  }

  check(response, {
    'login HTTP 200': (r) => r.status === 200
  });
}
