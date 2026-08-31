'use strict';

const dialogs = {
  login: document.getElementById('log-dialog'),
  register: document.getElementById('regi-dialog')
};
const forms = {
  login: document.getElementById('log-form'),
  register: document.getElementById('regi-form')
};
const error_messages = {
  login: document.getElementById('log-error-message'),
  register: document.getElementById('regi-error-message')
};
const param = new URLSearchParams(window.location.search);

//エラーメッセージ
{
  if (param.has('error')) {
    history.replaceState(null, '', window.location.pathname);
    error_messages['login'].textContent = '入力情報が不正です';
    dialogs['login'].showModal();
  } else {
    error_messages['login'].textContent = '';
  }
}

//ウィンドウポップアップ
{
  document.querySelectorAll('.account-button').forEach((button) => {
    button.addEventListener('click', (e) => {
      dialogs[e.currentTarget.dataset.type].showModal();
    });
  });

  document.querySelectorAll('.exit').forEach((button) => {
    button.addEventListener('click', (e) => {
      const datatype = e.currentTarget.dataset.type;
      error_messages[datatype].textContent = '';
      forms[datatype].reset();
      dialogs[datatype].close();
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
}

const inputName = document.getElementById('regi-Name');

const inputPass = document.getElementById('regi-Pass');

//httpリクエスト処理
async function accountControl() {
  try {
    const response = await fetch(
      'http://localhost:8080/accountController/register',
      {
        method: 'POST',
        headers: {
          'Content-type': 'application/json'
        },
        body: JSON.stringify({
          username: inputName.value,
          password: inputPass.value
        })
      }
    );
  } catch (error) {
    console.error('error');
  }
}

//新規登録
{
  document.querySelectorAll('.entry').forEach((entry) => {
    entry.addEventListener('click', (e) => {
      const datatype = e.currentTarget.dataset.type;

      if (document.getElementById('regi-Pass2').value !== inputPass.value) {
        error_messages[datatype].textContent = 'パスワードが異なっています';
        return;
      } else {
        error_messages[datatype].textContent = '';
      }
      accountControl();
    });
  });
}

const tools = {
  ffmpeg: document.getElementById('ffmpeg-detail')
  //以後他機能実装のためにオブジェクト化
};

//表示分岐
{
  document.querySelectorAll('.details').forEach((detail) => {
    const tool = tools[detail.dataset.tool];
    let mode;

    //モード選択分岐
    tool.querySelectorAll('input[name="ffmpeg-mode"]').forEach((input) => {
      input.addEventListener('change', (e) => {
        mode = e.currentTarget.value;

        const section = tool.querySelector(`section[data-mode="${mode}"]`);

        tool.querySelectorAll('.questions-mode').forEach((another) => {
          const style = another.style;
          if (another == section) {
            style.display = 'block';
          } else {
            style.display = 'none';
          }
        });
      });
    });

    //オペレーション選択分岐
    tool.querySelectorAll('input[name="operation"]').forEach((input) => {
      input.addEventListener('change', (e) => {
        const operation = e.currentTarget.value;

        tool.querySelectorAll('.operation-option').forEach((option) => {
          const style = option.style;

          if (option.dataset.operation === operation) {
            style.display = 'block';
          } else {
            style.display = 'none';
          }
        });

        tool.querySelectorAll('.transition').forEach((another) => {
          const style = another.style;

          if (another.parentElement.classList.contains(`${mode}-form`)) {
            style.display = 'block';
          } else if (!mode) {
            return;
          } else {
            style.display = 'none';
          }
        });
      });
    });
  });
}
