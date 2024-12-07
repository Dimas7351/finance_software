import 'package:firebase_auth/firebase_auth.dart';
import 'package:flutter/material.dart';
import 'package:flutter_firebase_auth/screens/account_screen.dart';
import 'package:flutter_firebase_auth/screens/login_screen.dart';
import 'package:flutter_firebase_auth/screens/signup_screen.dart';

class HomeScreen extends StatelessWidget {
  const HomeScreen({super.key});

  @override
  Widget build(BuildContext context) {
    final user = FirebaseAuth.instance.currentUser;

    return Scaffold(
      resizeToAvoidBottomInset: false,
      appBar: AppBar(
        title: const Text('Главная страница'),
        actions: [
          IconButton(
            onPressed: () {
              if (user == null) {
                Navigator.push(
                  context,
                  MaterialPageRoute(builder: (context) => const LoginScreen()),
                );
              } else {
                Navigator.push(
                  context,
                  MaterialPageRoute(
                      builder: (context) => const AccountScreen()),
                );
              }
            },
            icon: Icon(
              Icons.person,
              color: (user == null) ? Colors.blue : Colors.green,
            ),
          ),
        ],
      ),
      body: SafeArea(
        child: Center(
          child: (user == null)
              ? const WelcomeMessage()
              : const Text('Контент для ЗАРЕГИСТРИРОВАННЫХ в системе'),
        ),
      ),
    );
  }
}

class WelcomeMessage extends StatelessWidget {
  const WelcomeMessage({super.key});

  @override
  Widget build(BuildContext context) {
    return Container(
      padding: const EdgeInsets.all(16.0),

      child: Column(
        mainAxisSize: MainAxisSize.min,
        children: [
          const Text(
            'Здравствуйте!',
            style: TextStyle(
              fontSize: 18,
              fontWeight: FontWeight.bold,
            ),
          ),
          const SizedBox(height: 8),
          const Text(
            'Рады приветствовать вас в нашем приложении! Здесь вы сможете получить детальную аналитику по своим банковским аккаунтам, получить прогнозы на текущий месяц, советы по экономии и многое другое. Приступим!',
            textAlign: TextAlign.center,
            style: TextStyle(fontSize: 14),
          ),
          const SizedBox(height: 16),
          Row(
            mainAxisAlignment: MainAxisAlignment.spaceEvenly,
            children: [
              OutlinedButton(
                style: ButtonStyle(side: WidgetStateProperty.all(BorderSide (color: Colors.green))),
                onPressed: () {
                  Navigator.push(
                    context,
                    MaterialPageRoute(
                        builder: (context) => const LoginScreen()),
                  );
                },
                child: const Text('Вход'),
              ),
              OutlinedButton(
                style: ButtonStyle(side: WidgetStateProperty.all(BorderSide (color: Colors.green))),
                onPressed: () {
                  Navigator.push(
                    context,
                    MaterialPageRoute(
                        builder: (context) => const SignUpScreen()),
                  );                },
                child: const Text('Регистрация'),
              ),
            ],
          ),
        ],
      ),
    );
  }
}
