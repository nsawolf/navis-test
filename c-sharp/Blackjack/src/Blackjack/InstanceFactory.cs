using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace Blackjack
{
    public class InstanceFactory
    {
    }


    public class InstanceFactory<T> : IDisposable
    {
        private Func<T> builder;
        private Func<T> overrideBuilder;

        public InstanceFactory(Func<T> builder)
        {
            this.builder = builder;
        }

        public T make()
        {
            if (overrideBuilder == null)
                return builder.Invoke();
            return overrideBuilder.Invoke();
        }

        public void Dispose()
        {
            overrideBuilder = null;
        }

        public InstanceFactory<T> overide(Func<T> builder) {
           this.overrideBuilder = builder;
           return this;
       }   
  }
}
