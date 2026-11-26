import http from 'k6/http';
import { check } from 'k6';

let failuresLogged = 0;

export const options = {
  scenarios: {
    hpa_ramp: {
      executor: 'ramping-vus',
      startVUs: 0,
      stages: [
        { duration: '2m', target: 5 },
        { duration: '2m', target: 10 },
        { duration: '2m', target: 15 },
        { duration: '2m', target: 20 },
        { duration: '2m', target: 25 },
        { duration: '4m', target: 25 },
        { duration: '2m', target: 20 },
        { duration: '2m', target: 15 },
        { duration: '2m', target: 10 },
        { duration: '2m', target: 5 },
        { duration: '2m', target: 1 },
        { duration: '6m', target: 1 }
      ],
      gracefulRampDown: '30s'
    }
  }
};

export default function () {
  const email = encodeURIComponent(__ENV.TEST_EMAIL || '');
  const senha = encodeURIComponent(__ENV.TEST_PASSWORD || '');
  const baseUrl = __ENV.BASE_URL || 'http://192.168.2.134:30081';

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
