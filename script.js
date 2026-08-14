'use strict';

const dialogs = {
  login: document.getElementById('log-dialog'),
  register: document.getElementById('regi-dialog')
};

//ウィンドウポップアップ
document.querySelectorAll('.account-button').forEach((button) => {
  button.addEventListener('click', (e) => {
    dialogs[e.currentTarget.dataset.type].showModal();
  });
});

document.querySelectorAll('.exit').forEach((button) => {
  button.addEventListener('click', (e) => {
    dialogs[e.currentTarget.dataset.type].close();
  });
});

document.querySelectorAll('dialog').forEach((dialog) => {
  dialog.addEventListener('click', (e) => {
    const rect = dialog.getBoundingClientRect();

    const isInDialog =
      rect.top <= e.clientY &&
      e.clientY <= rect.bottom &&
      rect.left <= e.clientX &&
      e.clientX <= rect.right;

    if (!isInDialog) {
      dialog.close();
    }
  });
});

const inputName = {
  login: document.getElementById('loginName'),
  register: document.getElementById('regi-Name')
};

const inputPass = {
  login: document.getElementById('loginPass'),
  register: document.getElementById('regi-Pass')
};

let error_message = document.getElementById('error-message');

//httpリクエスト処理
async function accountControl(e) {
  try {
    const datatype = e.currentTarget.dataset.type;

    const response = await fetch(
      `http://localhost:8080/accountController/${datatype}`,
      {
        method: 'POST',
        headers: {
          'Content-type': 'application/json'
        },
        body: JSON.stringify({
          username: inputName[datatype].value,
          password: inputPass[datatype].value
        })
      }
    );

    const bool = await response.json();

    if (datatype === 'login') {
      if (bool === true) {
        window.location.replace('logged_in.html');
      } else {
        error_message.textContent = '入力情報が不正です';
      }
    }
    if (datatype === 'register') {
      if (bool === true) {
        window.location.replace('account_page.html');
      } else {
        error_message.textContent = '既に登録されています';
      }
    }
  } catch (error) {
    console.error('error');
  }
}

//リスナー登録
document.querySelectorAll('.enter').forEach((enter) => {
  enter.addEventListener('click', (e) => {
    const datatype = e.currentTarget.dataset.type;

    if (datatype === 'register') {
      if (
        document.getElementById('regi-Pass2').value !==
        inputPass[datatype].value
      ) {
        error_message.textContent = 'パスワードが異なっています';
        return;
      } else {
        error_message.textContent = '';
      }
    }

    accountControl(e);
  });
});
