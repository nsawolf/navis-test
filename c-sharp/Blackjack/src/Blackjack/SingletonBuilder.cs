using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace Blackjack
{
    public class SingletonBuilder<T>
    {
        private T value;

        private SingletonBuilder(T v)
        {
            value = v;
        }

        private Func<T> getBuilder()
        {
            return () => value;
        }
        
        public static Func<T> builder(T v)
        {
            return new SingletonBuilder<T>(v).getBuilder();
        }
    }
}
